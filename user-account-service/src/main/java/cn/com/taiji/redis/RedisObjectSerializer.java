package cn.com.taiji.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis对象序列化及反序列化操作
 * @author liguobao
 *
 */
public class RedisObjectSerializer implements RedisSerializer<Object>{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	/** 序列化操作器 */
	private Converter<Object, byte[]> serializer = new SerializingConverter();
	
	/** 反序列化操作器 */
	private Converter<byte[], Object> deserializer = new DeserializingConverter();
	
	/** 空数组 */
	static final byte[] EMPTY_ARRAY = new byte[0];

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
	 */
	public Object deserialize(byte[] bytes) {
		if (isEmpty(bytes)) {
			return null;
		}
		try {
			return deserializer.convert(bytes);
		} catch (Exception ex) {
			log.error("Cannot deserialize", ex);
			throw new SerializationException("Cannot deserialize", ex);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
	 */
	public byte[] serialize(Object object) {
		if (object == null) {
			return EMPTY_ARRAY;
		}
		try {
			return serializer.convert(object);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return EMPTY_ARRAY;
		}
	}

	/**
	 * @Title: isEmpty
	 * @Description: 是否为空判断
	 * @param data 对象字节数组
	 * @return boolean 是否为空
	 * @throws
	 */
	private boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

}
