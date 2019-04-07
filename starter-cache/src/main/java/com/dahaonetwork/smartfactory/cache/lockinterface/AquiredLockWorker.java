package com.dahaonetwork.smartfactory.cache.lockinterface;

/**
 * 获取锁后需要处理的逻辑
 * @author liguobao
 *
 * @param <T>
 */
public interface AquiredLockWorker<T> {
	  T invokeAfterLockAquire() throws Exception;

}
