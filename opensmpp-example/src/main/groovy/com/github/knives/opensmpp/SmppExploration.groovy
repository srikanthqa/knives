package com.github.knives.opensmpp
import org.smpp.pdu.SubmitSM
import org.smpp.pdu.Address
import org.smpp.Data
import org.apache.commons.codec.binary.Hex


private static Address createAddress(String address) {
	Address addressInst = new Address();
	addressInst.setTon((byte) 1); // national ton
	addressInst.setNpi((byte) 1); // numeric plan indicator
	addressInst.setAddress(address, Data.SM_ADDR_LEN);
	return addressInst;
}

final SubmitSM request = new SubmitSM();
//request.setServiceType(serviceType);
request.setSourceAddr(createAddress("1234567890"));
request.setDestAddr(createAddress("1234567890"));
request.setShortMessage("hello world");
//request.setScheduleDeliveryTime(deliveryTime);
request.setReplaceIfPresentFlag((byte) 0);
request.setEsmClass((byte) 0);
request.setProtocolId((byte) 0);
request.setPriorityFlag((byte) 0);
request.setRegisteredDelivery((byte) 0);
request.setDataCoding((byte) 0);
request.setSmDefaultMsgId((byte) 0);

// ugly display
println request.getData().getHexDump()
//println Hex.encodeHexString(request.getBody().getBuffer())
