package com.framework.autorun;

import com.qimpay.weixin.DownLoadBill;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class DownloadBillServlet extends HttpServlet {
    public class DownloadBillThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    DownLoadBill.dbill("");
                    // 休眠7000秒
                    Thread.sleep(DEFAULTEXPIRESTIME*1000);
                }
                catch (Exception exception) {
                    try {
                        // 60秒后再获取
                        Thread.sleep(60 * 1000);
                    }
                    catch (InterruptedException e1) {
                    }
                }
            }
        }

        private final static int DEFAULTEXPIRESTIME = 3600*24;
    }

    public void init() throws ServletException {
        new Thread(new DownloadBillThread()).start();
    }
}
