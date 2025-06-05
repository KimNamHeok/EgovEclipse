package kr.or.ddit.dummy;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)  { 
        System.out.println("서버 구동");
    }


    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("서버 종료");
    }
	
}
