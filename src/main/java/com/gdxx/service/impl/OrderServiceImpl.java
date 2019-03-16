package com.gdxx.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdxx.dao.OrderDao;
import com.gdxx.dao.OrderDetailDao;
import com.gdxx.dao.ProductDao;
import com.gdxx.dao.ReceivingAddressDao;
import com.gdxx.dao.UserProductMapDao;
import com.gdxx.dao.UserShopMapDao;
import com.gdxx.dto.OrderExecution;
import com.gdxx.entity.Order;
import com.gdxx.entity.OrderDetail;
import com.gdxx.entity.PersonInfo;
import com.gdxx.entity.Product;
import com.gdxx.entity.ReceivingAddress;
import com.gdxx.entity.Shop;
import com.gdxx.entity.UserProductMap;
import com.gdxx.entity.UserShopMap;
import com.gdxx.enums.OrderStateEnum;
import com.gdxx.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private ReceivingAddressDao receivingAddressDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserProductMapDao userProductMapDao;
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Override
	@Transactional
	public OrderExecution addOrderHadPayed(ReceivingAddress address, PersonInfo user, double totalMon,
			String orderMsg) {
		Order order = new Order();
		order.setUserId(user.getUserId());
		String orderId = getOrderIdByTime(user.getUserId());
		order.setOrderId(orderId);
		if (address.getAddressId() != null) {
			ReceivingAddress receAddress = receivingAddressDao.queryReceivingAddressById(address.getAddressId());
			if (receAddress != null) {
				order.setReceivingAddress(address);
				List<OrderDetail> orderDetailList = new ArrayList<>();
				List<UserProductMap> userProductMapList = new ArrayList<>();
				Map<Long, Integer> userShopPointMap = new HashMap<>();
				String[] group = orderMsg.split(",");
				double checkMoney = 0.0;
				for (int i = 0; i < group.length; i++) {
					long productId = Long.parseLong((group[i].split("-"))[0]);
					int productNum = Integer.parseInt((group[i].split("-"))[1]);
					Product product = productDao.queryProductById(productId);
					if (product != null && product.getEnableStatus() == 1) {
						if (userShopPointMap.containsKey(product.getShop().getShopId())) {
							Integer tempPoint = userShopPointMap.get(product.getShop().getShopId());
							tempPoint += productNum * product.getPoint();
							userShopPointMap.put(product.getShop().getShopId(), tempPoint);
						} else {
							userShopPointMap.put(product.getShop().getShopId(), product.getPoint() * productNum);
						}
						UserProductMap userProductMap = new UserProductMap();
						userProductMap.setCreateTime(new Date());
						userProductMap.setProduct(product);
						userProductMap.setShop(product.getShop());
						userProductMap.setUser(user);
						PersonInfo operator = new PersonInfo();
						operator.setUserId(2L);
						userProductMap.setOperator(operator);
						userProductMap.setPoint(product.getPoint());
						for (int j = 0; j < productNum; j++) {
							userProductMapList.add(userProductMap);
						}
						OrderDetail orderDetail = new OrderDetail();
						orderDetail.setOrderId(order.getOrderId());
						orderDetail.setProductId(productId);
						orderDetail.setShopId(product.getShop().getShopId());
						orderDetail.setProductNumber(productNum);
						orderDetailList.add(orderDetail);
						checkMoney += Double.parseDouble(product.getPromotionPrice()) * productNum;
					} else {
						return new OrderExecution(OrderStateEnum.PRODUCT_NULL);
					}
				}
				if (totalMon == checkMoney) {
					order.setCreateTime(new Date());
					order.setMoneyAccount(totalMon);
					order.setOrderStatus(2);
					try {
						int effNum1 = orderDao.insertOrder(order);
						if (effNum1 <= 0) {
							return new OrderExecution(OrderStateEnum.INNER_ERROR);
						}
						if (orderDetailList.size() > 0) {
							int effNum2 = orderDetailDao.insertOrderDetailList(orderDetailList);
							if (effNum2 <= 0) {
								return new OrderExecution(OrderStateEnum.INNER_ERROR);
							}
						}
						for (int i = 0; i < userProductMapList.size(); i++) {
							int effNum3 = userProductMapDao.insertUserProductMap(userProductMapList.get(i));
							if (effNum3 <= 0) {
								return new OrderExecution(OrderStateEnum.INNER_ERROR);
							}
						}

						for (Entry<Long, Integer> usp : userShopPointMap.entrySet()) {
							Shop shop = new Shop();
							shop.setShopId(usp.getKey());
							UserShopMap userShopMap = userShopMapDao.queryUserShopMap(user.getUserId(),
									shop.getShopId());
							userShopMap.setPoint(userShopMap.getPoint() + usp.getValue());
							int effNum4 = userShopMapDao.updateUserShopMapPoint(userShopMap);
							if (effNum4 <= 0) {
								return new OrderExecution(OrderStateEnum.INNER_ERROR);
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
					return new OrderExecution(OrderStateEnum.SUCCESS);
				} else {
					return new OrderExecution(OrderStateEnum.CHENK_MONEY_ERROR);
				}
			} else {
				return new OrderExecution(OrderStateEnum.ADDRESS_INVALID);
			}
		}
		return new OrderExecution(OrderStateEnum.INNER_ERROR);
	}

	@Override
	public OrderExecution getOrderListByIdAndStatus(Long shopId, Long userId, Integer orderStatus) {
		Map<String, List<Product>> orderProductMap = new HashMap<>();
		List<Order> orderList = null;
		if (userId != null) {
			orderList = orderDao.queryOrderListByIdAndStatus(null, userId, orderStatus);
		}
		if (shopId != null) {
			orderList = orderDao.queryOrderListByIdAndStatus(shopId, null, orderStatus);
		}
		if (orderList.size() > 0) {
			for (int i = 0; i < orderList.size(); i++) {
				List<Product> productList = new ArrayList<>();
				Order order = orderList.get(i);
				List<OrderDetail> orderDetailList = order.getOrderDetailList();
				for (OrderDetail orderDetail : orderDetailList) {
					Long productId = orderDetail.getProductId();
					Product product = productDao.queryProductById(productId);
					if (product != null) {
						productList.add(product);
					}
				}
				orderProductMap.put(order.getOrderId(), productList);
			}
			OrderExecution orderExecution = new OrderExecution();
			orderExecution.setOrderList(orderList);
			orderExecution.setOrderIdProductListMap(orderProductMap);
			orderExecution.setStateInfo(OrderStateEnum.SUCCESS.getStateInfo());
			return orderExecution;
		} else {
			return new OrderExecution(OrderStateEnum.NULL_ORDER);
		}
	}

	public String getOrderIdByTime(Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			result += random.nextInt(10);
		}
		return newDate + result + String.valueOf(userId);
	}

	@Override
	public OrderExecution addOrderNotPayed(ReceivingAddress address, PersonInfo user, Double totalMon,
			String orderMsg) {
		Order order = new Order();
		order.setUserId(user.getUserId());
		String orderId = getOrderIdByTime(user.getUserId());
		order.setOrderId(orderId);
		if (address.getAddressId() != null) {
			ReceivingAddress receAddress = receivingAddressDao.queryReceivingAddressById(address.getAddressId());
			if (receAddress != null) {
				order.setReceivingAddress(address);
				List<OrderDetail> orderDetailList = new ArrayList<>();
				String[] group = orderMsg.split(",");
				double checkMoney = 0.0;
				for (int i = 0; i < group.length; i++) {
					long productId = Long.parseLong((group[i].split("-"))[0]);
					int productNum = Integer.parseInt((group[i].split("-"))[1]);
					Product product = productDao.queryProductById(productId);
					if (product != null && product.getEnableStatus() == 1) {
						OrderDetail orderDetail = new OrderDetail();
						orderDetail.setOrderId(order.getOrderId());
						orderDetail.setProductId(productId);
						orderDetail.setShopId(product.getShop().getShopId());
						orderDetail.setProductNumber(productNum);
						orderDetailList.add(orderDetail);
						checkMoney += Double.parseDouble(product.getPromotionPrice()) * productNum;
					} else {
						return new OrderExecution(OrderStateEnum.PRODUCT_NULL);
					}
				}
				if (totalMon == checkMoney) {
					order.setCreateTime(new Date());
					order.setMoneyAccount(totalMon);
					order.setOrderStatus(1);
					try {
						int effNum1 = orderDao.insertOrder(order);
						int effNum2 = orderDao.updateExpireTime(order.getOrderId());
						if (effNum1 <= 0 || effNum2 <= 0) {
							return new OrderExecution(OrderStateEnum.INNER_ERROR);
						}
						if (orderDetailList.size() > 0) {
							int effNum3 = orderDetailDao.insertOrderDetailList(orderDetailList);
							if (effNum3 <= 0) {
								return new OrderExecution(OrderStateEnum.INNER_ERROR);
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
					return new OrderExecution(OrderStateEnum.SUCCESS);
				} else {
					return new OrderExecution(OrderStateEnum.CHENK_MONEY_ERROR);
				}
			} else {
				return new OrderExecution(OrderStateEnum.ADDRESS_INVALID);
			}
		} else {
			return new OrderExecution(OrderStateEnum.NULL_ORDER);
		}

	}

	@Override
	public OrderExecution modifyOrderStatus(String orderId, String operationType) {
		Integer orderStatus = null;
		if (operationType.equals("cancel")) {
			orderStatus = 0;
		}
		if (operationType.equals("send")) {
			orderStatus = 3;
		}

		if (orderStatus != null) {
			try {
				int effNum = orderDao.updateOrderStatus(orderId, orderStatus);
				if (effNum <= 0) {
					return new OrderExecution(OrderStateEnum.INNER_ERROR);
				}
				return new OrderExecution(OrderStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} else {
			return new OrderExecution(OrderStateEnum.NULL_ORDER);
		}

	}

	@Override
	public OrderExecution dealOrderHadCancel(PersonInfo user, String orderId) {
		Order order = orderDao.queryOrderByOrderId(orderId);
		List<OrderDetail> orderDetailList = order.getOrderDetailList();
		List<UserProductMap> userProductMapList = new ArrayList<>();
		Map<Long, Integer> userShopPointMap = new HashMap<>();
		if (orderDetailList.size() > 0) {
			for (OrderDetail orderDetail : orderDetailList) {
				Product product = productDao.queryProductById(orderDetail.getProductId());
				if (product != null && product.getEnableStatus() == 1) {
					if (userShopPointMap.containsKey(product.getShop().getShopId())) {
						Integer tempPoint = userShopPointMap.get(product.getShop().getShopId());
						tempPoint += orderDetail.getProductNumber() * product.getPoint();
						userShopPointMap.put(product.getShop().getShopId(), tempPoint);
					} else {
						userShopPointMap.put(product.getShop().getShopId(),
								product.getPoint() * orderDetail.getProductNumber());
					}
					UserProductMap userProductMap = new UserProductMap();
					userProductMap.setCreateTime(new Date());
					userProductMap.setProduct(product);
					userProductMap.setShop(product.getShop());
					userProductMap.setUser(user);
					PersonInfo operator = new PersonInfo();
					operator.setUserId(2L);
					userProductMap.setOperator(operator);
					userProductMap.setPoint(product.getPoint());
					for (int j = 0; j < orderDetail.getProductNumber(); j++) {
						userProductMapList.add(userProductMap);
					}
				}
			}
			try {
				int effNum1 = orderDao.updateOrderStatus(orderId, 2);
				if (effNum1 <= 0) {
					return new OrderExecution(OrderStateEnum.INNER_ERROR);
				}
				for (int i = 0; i < userProductMapList.size(); i++) {
					int effNum3 = userProductMapDao.insertUserProductMap(userProductMapList.get(i));
					if (effNum3 <= 0) {
						return new OrderExecution(OrderStateEnum.INNER_ERROR);
					}
				}
				for (Entry<Long, Integer> usp : userShopPointMap.entrySet()) {
					Shop shop = new Shop();
					shop.setShopId(usp.getKey());
					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(user.getUserId(), shop.getShopId());
					userShopMap.setPoint(userShopMap.getPoint() + usp.getValue());
					int effNum4 = userShopMapDao.updateUserShopMapPoint(userShopMap);
					if (effNum4 <= 0) {
						return new OrderExecution(OrderStateEnum.INNER_ERROR);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			return new OrderExecution(OrderStateEnum.SUCCESS);
		} else {
			return new OrderExecution(OrderStateEnum.NULL_ORDER);
		}

	}

}
