package cn.com.taiji.configure;

import com.dahaonetwork.smartfactory.cache.exception.UnableToAquireLockException;
import com.dahaonetwork.smartfactory.cache.lockinterface.AquiredLockWorker;
import com.dahaonetwork.smartfactory.cache.lockinterface.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * 在不同进程需要互斥地访问共享资源时，分布式锁是一种非常有用的技术手段。实现高效的分布式锁有三个属性需要考虑：
		安全属性：互斥，不管什么时候，只有一个客户端持有锁
		效率属性A:不会死锁
		效率属性B：容错，只要大多数redis节点能够正常工作，客户端端都能获取和释放锁。
 * @author liguobao
 *
 */
@Component
public class RedisReadLocker implements DistributedLocker{
	
	private final static String LOCKER_PREFIX = "lock:";
	@Autowired
    RedissonConnector redissonConnector;

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
		// TODO Auto-generated method stub
		return lock(resourceName, worker, 100);
	}

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime)
			throws UnableToAquireLockException, Exception {
		// TODO Auto-generated method stub
		RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
	}

}
