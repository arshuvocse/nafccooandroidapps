package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTransport {
    int TransportId;
    String  TransportName;
    public ModelTransport() {
    }

    public int getTransportId() {
        return TransportId;
    }

    public void setTransportId(int transportId) {
        TransportId = transportId;
    }

    public String getTransportName() {
        return TransportName;
    }

    public void setTransportName(String transportName) {
        TransportName = transportName;
    }

    @Override
    public String toString() {
        return TransportName;
    }
}
