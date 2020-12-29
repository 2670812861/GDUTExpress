package com.kaikeba.bean;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-08 18:57
 */
public class Message {
    //ǰ�˿���������Ӧ���������ģ� {status:0 result:"",data:{}}

    //״̬��:0��ʾ�ɹ� -1��ʾʧ��
    private int status;
    //��Ϣ����
    private String result;
    //��Ϣ��Я����һ������
    private Object data;

    public Message() {
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
