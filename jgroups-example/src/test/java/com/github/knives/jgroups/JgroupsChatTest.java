package com.github.knives.jgroups;

import java.net.URL;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

public class JgroupsChatTest {

	public static void main(String[] args) throws Exception {
        JChannel channel = new JChannel(JgroupsChatTest.class.getResourceAsStream("/jgroups/udp.xml"));  

        channel.setReceiver(new ReceiverAdapter() {
            public void viewAccepted(View new_view) {
                System.out.println("view: " + new_view);
            }

            public void receive(Message msg) {
                Address sender=msg.getSrc();
                System.out.println(msg.getObject() + " [" + sender + "]");
            }
        });

        // name of the channel
        channel.connect("ChatCluster");


        for(;;) {
            String line = Util.readStringFromStdin(": ");
            channel.send(null, line);
        }
	}

}
