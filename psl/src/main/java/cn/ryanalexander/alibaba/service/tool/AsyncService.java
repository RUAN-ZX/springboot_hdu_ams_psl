package cn.ryanalexander.alibaba.service.tool;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    @Async
    public void hello(){
        try{
            Thread.sleep(3000);

        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Data being calculated");

    }

}
