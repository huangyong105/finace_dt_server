package cn.com.taiji.configure;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedissonConnector {
	RedissonClient redisson;
    @PostConstruct
    public void init(){
        Config config = new Config();
       config.useSingleServer().setAddress("47.101.147.30:6379");
//        config.useMasterSlaveConnection().setMasterAddress("127.0.0.1:6379").addSlaveAddress("127.0.0.1:6389").addSlaveAddress("127.0.0.1:6399");
//        config.useSentinelConnection().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379");
       // config.useClusterServers().addNodeAddress("39.97.173.139:7000");
        redisson = Redisson.create(config);
    }

    public RedissonClient getClient(){
        return redisson;
    }

}
