package com.gdxx.dto;

import java.util.List;

import com.gdxx.entity.PersonInfo;
import com.gdxx.enums.PersonInfoStateEnum;

/**
 * ��װִ�к���
 */
public class PersonInfoExecution {

	// ���״̬
	private int state;

	// ״̬��ʶ
	private String stateInfo;

	// ��������
	private int count;

	// ������personInfo����ɾ�ĵ��̵�ʱ���ã�
	private PersonInfo personInfo;

	// ��ȡ��personInfo�б�(��ѯ�����б��ʱ����)
	private List<PersonInfo> personInfoList;

	public PersonInfoExecution() {
	}

	// ʧ�ܵĹ�����
	public PersonInfoExecution(PersonInfoStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// �ɹ��Ĺ�����
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,
			PersonInfo personInfo) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.personInfo = personInfo;
	}

	// �ɹ��Ĺ�����
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,
			List<PersonInfo> personInfoList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.personInfoList = personInfoList;
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

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public List<PersonInfo> getPersonInfoList() {
		return personInfoList;
	}

	public void setPersonInfoList(List<PersonInfo> personInfoList) {
		this.personInfoList = personInfoList;
	}

}