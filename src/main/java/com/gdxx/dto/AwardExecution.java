package com.gdxx.dto;



import java.util.List;

import com.gdxx.entity.Award;
import com.gdxx.enums.AwardStateEnum;


public class AwardExecution {
	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��������
	private int count;

	// ������award����ɾ����Ʒ��ʱ���ã�
	private Award award;

	// ��ȡ��award�б�(��ѯ��Ʒ�б��ʱ����)
	private List<Award> awardList;

	public AwardExecution() {
	}

	// ʧ�ܵĹ�����
	public AwardExecution(AwardStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public AwardExecution(AwardStateEnum stateEnum, Award award) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.award = award;
	}

	// �ɹ��Ĺ�����
	public AwardExecution(AwardStateEnum stateEnum,
			List<Award> awardList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.awardList = awardList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public List<Award> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<Award> awardList) {
		this.awardList = awardList;
	}

}
