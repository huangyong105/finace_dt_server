package cn.com.taiji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * 三个主要配置： ClientDetailsServiceConfigurer：配置客户端信息服务
 * AuthorizationServerSecurityConfigurer:配置token相关endpoint的设置
 * AuthorizationServerEndpointsConfigurer:配置授权和token的endpoint,以及token service
 * 
 * @author zhuohao
 */

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//    private DataSource dataSource;
	
	@Autowired
    private RedisConnectionFactory connectionFactory;
	
	/**
	 * 三个实现方式如下：
	 * (1)默认的InMemoryTokenStore对于单台服务器的实现，是一个很好的选择
	 * (2)JdbcTokenStore是一个JDBC版本的实现。要使用JdbcTokenStore，你需要引用spring-jdbc库。
	 * (3)JSON Web Token (JWT)版本的实现，将所有的信息编码到token本身之中。这个实现的夜歌弊端在于，
	 * 你不能轻易的使token失效。所以其一般配以较短的失效时间，而失效时再refresh token中处理。
	 * 另一个弊端是，如果存储了过多的用户信息在token中，该token可能变得十分庞大。
	 * 从这个意义上来说JwtTokenStore并不是一个真正的Store，因为其并不持久化任何数据，但其处理了token与授权信息之间的转换工作。
	 * @return
	 */
    @Bean // 声明TokenStore实现
    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//        return new InMemoryTokenStore();
        return new RedisTokenStore(connectionFactory);
    }


	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;


	@Bean("jdbcTokenStore")
	public JdbcTokenStore getJdbcTokenStore() {
		return new JdbcTokenStore(dataSource);
	}


	@Bean("jdbcClientDetailsService")
	public JdbcClientDetailsService getJdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}



	/**
     * oauth_access_token表是存放访问令牌的
     * 但是并没有直接在字段中存放token。Spring 使用OAuth2AccessToken来抽象与令牌有关的所有属性
     * 在写入到数据库时，Spring将该对象通过JDK自带的序列化机制序列成字节直接保存到了该表的token字段中
     * 也就是说，如果只看数据表你是看不出access_token的值是多少，过期时间等信息的
     */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(this.authenticationManager).reuseRefreshTokens(false)
			.tokenStore(getJdbcTokenStore()).setClientDetailsService(getJdbcClientDetailsService());

	}
	
	
	
	@Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

	/**
	 * 也可以放在application.yml配置文件里面
	 *
	 * security: oauth2: client: client-id: timeyang client-secret: timeyangsecret
	 * scope: read,write auto-approve-scopes: '.*'
	 * 
	 * clientId: 必需，客户端ID secret
	 * : 对于信任的客户端必需 scope : 客户端允许访问资源的范围，如果该字段未定义或为空，则客户端访问范围不受限制。
	 * authorizedGrantTypes: 指定客户端支持的grant_type authorities: 客户端所拥有的Spring
	 * Security的权限值
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("zhuohao")//客户端ID
			.secret("zhuohao888")//密码
			.authorizedGrantTypes("authorization_code", "refresh_token","password")
			.scopes("openid")//授权用户的操作权限
			.accessTokenValiditySeconds(30)//token有效期：单位秒
			.refreshTokenValiditySeconds(6000)//刷新token有效期：单位秒
			.autoApprove(false);
		
	}
	

}
