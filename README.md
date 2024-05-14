# 基于微服务的Webpos

**211275032 汪科**

## 系统实现

在本次试验中，希望将以前的Webpos重写为基于微服务架构的Webpos，这样做有很多好处：

* **可扩展性**：微服务架构可以独立扩展服务。一般来说，用户浏览商品的频繁程度比下单更甚，因此我们可以考虑分离下单和对商品的浏览功能，并根据需要对服务能力进行扩展。
* **易于理解和维护**：每个服务都是一个小的、独立的应用，它们比一个大的单体应用更容易理解和维护。
* **独立部署**：每个微服务可以独立部署，这意味着可以更频繁地部署新的功能，而不需要每次都部署整个应用。

基本架构如下：

![img](https://www.plantuml.com/plantuml/png/XPFDJiCm48JlVWgJ4mZb1N90ZL3K8XMeMYJEMtiJHJLnyK-XGhmxJcBQsdN19sjcbFbiRoRQW3BsJP0hZdNR8LrMzo_bqtHUKIP0QqAsm3RG8CqUMwOaVSNrGguo-aMeMyTqLbc56w0hL3jK6GLDHsKKVBvVnIrLsBJQeEgzbk7rpQd9MytaBZQ_ILmu1OCViAS-DrHwpKjHgRG6b_XkKPkmSGq2EePp2VnYsq99Rfa9shoMR6lGUFkXgsNO1vnxBzJjbZl-fCLHO6oTGpuhxgxw575Gae1CeIiR_1H5md17IgWZsQbnpZKhSuV-kH-GPkMm3sbIJ-2-4unGdj_53rmSHacJoEhJqGCLumneQcGg7TpLFza0h3ZCh2PHPI9x90uA_qoItqlB1TOcTJBDGA0BJh3Z_V_y0m00)

### 搭建Eureka注册中心

Eureka由两个组件组成：Eureka服务器和Eureka客户端。

**Eureka服务器**是服务注册中心。微服务启动时，它会将自己的信息（如服务的IP地址、端口、服务名称等）注册到Eureka服务器上。这样，其他服务就可以从Eureka服务器上查找到这个服务。

**Eureka客户端**是一个Java库，微服务可以通过它来与Eureka服务器进行交互。

下面是示意图：

![img](https://repo-for-md.oss-cn-beijing.aliyuncs.com/uPic/uyswrvle4fwks_8dbc78ffc23b42209376f9910ceb9e9d.jpeg)

* 服务提供者首先向eureka注册服务信息
* 服务使用者从eureka注册中心拉取服务信息
* 远程调用服务

所以要首先搭建eureka-server，这部分在pos-discovery中完成。在pom.xml中添加完必要的注解之后，在类中添加：

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}
}
```

添加这两个注解意味着开启eureka注册中心功能。另外，还需要在application.yml中添加一些配置：

```yml
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  # standalone mode
  client:
    registerWithEureka: false # 这里设置为false，因为这个服务就是Eureka服务器，无需注册。
    fetchRegistry: false # 是否从Eureka服务器获取注册信息，这里设置为false，因为这个服务就是Eureka服务器，无需获取。
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # 默认区域的Eureka服务器URL

spring:
  application:
    name: dragonk_eureka_server # Spring Boot应用的名称
  cloud:
    config:
      discovery:
        enabled: false # 是否启用Spring Cloud Config的服务发现功能，这里设置为false，因为这个服务是Eureka服务器，无需发现其他服务。
```

### 动态路由负载均衡

这部分在gateway中完成。

API网关，也被称为Gateway，是微服务架构中的关键组件，它扮演着服务的门户角色，封装了客户端与服务端应用之间的交互。所有的客户端请求都会首先经过API网关，然后根据预定义的路由规则和断言条件，API网关将请求转发到相应的微服务。在这个过程中，路由定义了请求应该转发到哪个服务，而断言则定义了满足哪些条件的请求才会被转发。

除了路由和断言，API网关还提供了一系列的功能，包括请求过滤、流量控制、日志监控等。这些功能可以帮助我们更好地管理和控制微服务，提高系统的稳定性和性能。

同时，API网关也是一个微服务的客户端，它需要在Eureka服务注册中心上注册。可以使用@EnableEurekaClient注解来启用Eureka客户端，并在application.yml配置文件中配置路由规则和Eureka的相关信息。

在本次实验中使用uri到服务实例的映射方式来进行路由：

```yml
spring:
  application:
    name: gateway-services
  cloud:
    gateway:
      routes:
        - id: products-service
          # 负载均衡到名为`products-service`的服务实例
          uri: lb://products-service
          # 定义路由规则的条件
          predicates:
            - Path=/productsService/**
        - id: orders-service
          # 负载均衡到名为`orders-service`的服务实例
          uri: lb://orders-service
          # 定义路由规则的条件
          predicates:
            - Path=/ordersService/**
```

### 订单、商品服务

示例：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}
```

@EnableDiscoveryClient注解使得这个应用程序可以被服务发现组件（如Eureka、Consul等）发现和注册。

![image-20240515020315750](https://repo-for-md.oss-cn-beijing.aliyuncs.com/uPic/image-20240515020315750.png)

## 实验验证

在常见的购物场景中，用户可能会频繁地访问商品页面以获取产品信息，相比之下，将商品加入购物车并完成支付的行为（由订单服务处理）则较为少见。

因此在设计压力测试时，模拟了用户频繁请求产品服务，同时偶尔请求订单服务的情景。

扩展前，两个服务数量都为1，此时的性能：

![image-20240515015257704](https://repo-for-md.oss-cn-beijing.aliyuncs.com/uPic/image-20240515015257704.png)

将产品服务水平扩展到2个服务：

![image-20240515015335462](https://repo-for-md.oss-cn-beijing.aliyuncs.com/uPic/image-20240515015335462.png)

将订单服务水平扩展到2个服务，但是产品服务仍为1个：

![image-20240515015401995](https://repo-for-md.oss-cn-beijing.aliyuncs.com/uPic/image-20240515015401995.png)

可见水平扩展订单服务并不能提升整个服务的负载表现，但是扩展产品服务的提升较大，这也说明了服务的性能瓶颈在于产品服务。

## 断路器

断路器（Circuit Breaker）是一种软件设计模式，常用于微服务架构中来防止一个服务因为过多的失败请求而导致整个系统的失败。

> 在微服务架构中，如果一个服务不可用或响应时间过长，那么继续向该服务发送请求将会浪费资源并可能导致系统性能下降。断路器模式可以在服务出现问题时立即失败，而不是等待服务的响应。如果服务持续出现问题，断路器会"打开"，所有尝试访问该服务的请求都会立即失败，而不会进行实际的服务调用。在一段时间后，断路器会进入"半开"状态，允许部分请求通过以测试服务是否已恢复正常。如果服务已恢复，断路器将"关闭"，如果服务仍有问题，断路器将再次"打开"。

断路器模式可以防止连锁故障（Cascading Failure）的发生，即一个服务的失败导致依赖于它的其他服务也失败。这种模式也可以提高系统的弹性和可用性。

在本次试验中，我们设置在尝试获取一个不存在的产品（productId不存在）的情况下，而不是返回一个不优雅的404 NOT FOUND响应，通过使用断路器，系统可以更**优雅**地处理这种情况。

在实现上，当在`posServiceImp`类中的查找服务得到一个空结果时，会抛出一个`HttpClientErrorException`。这是一个特定的异常，通常在HTTP请求失败时抛出。

然后，在`ProductController`类中，断路器会捕获这个异常。如果断路器捕获到这个异常，它会激活，然后返回一个预设的结果。这个预设的结果是一个包含了默认值的产品对象，**这样用户就不会看到一个错误页面，而是看到一个提示商品不存在的信息。**

具体的代码如下：

```java
circuitBreaker.run(
    // 尝试执行的操作
    () -> ResponseEntity.ok(productMapper.toProductDto(posService.getProduct(productId))),
    // 在操作失败时执行的备用操作
    throwable -> ResponseEntity.ok(productMapper.toProductDto(new Product("null", "未找到任何产品", 0, "null", "null")))
);
```

