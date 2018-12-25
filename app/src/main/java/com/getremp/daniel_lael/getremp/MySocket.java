package com.getremp.daniel_lael.getremp;

import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MySocket {


    public void bdika() {
        try{
            final Socket socket = IO.socket("http://172.20.20.34:8000");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("message", "hi");
                    socket.disconnect();
                }

            }).on("message", new Emitter.Listener() {
                //message is the keyword for communication exchanges
                @Override
                public void call(Object... args) {
                    socket.emit("message", "hi");
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            });
            socket.connect();
            socket.emit("message", "hi");
            socket.send("LAEL");
        }
        catch(Exception e){
            Log.e("SOCKET", e.toString());
        }

    }
}
