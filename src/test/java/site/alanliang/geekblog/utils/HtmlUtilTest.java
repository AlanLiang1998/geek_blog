package site.alanliang.geekblog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HtmlUtilTest {
    @Test
    void test() {
        String text = "<h2 id=\"h2-spring-\"><a name=\"Spring框架：应用最广泛的框架\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Spring框架：应用最广泛的框架</h2><p>成功来自于理念：</p>\n" +
                "<ul>\n" +
                "<li>IoC（Inversion of Control，控制反转）</li><li>AOP（Aspect Oriented Programming，面向切面编程）</li></ul>\n" +
                "<h3 id=\"h3-spring-ioc\"><a name=\"Spring IoC\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Spring IoC</h3><p>IoC是一个容器，容器的目标是管理Java Bean和他们之间的关系。</p>\n" +
                "<p>JavaBean之间存在一定的依赖关系，比如班级是依赖学生和老师组成的。</p>\n" +
                "<p>Spring Ioc管理对象及其依赖关系，采用的不是人为的主动创建，而是由Spring IoC自己通过描述创建的，也就是说Spring是依靠描述来完成对象的创建及其依赖关系的。</p>\n" +
                "<p>例子：使用插座</p>\n" +
                "<p><img src=\"http://q8rsjstig.bkt.clouddn.com/note/使用插座图.jpg\" alt=\"使用插座\"></p>\n" +
                "<p>用传统的方式管理对象</p>\n" +
                "<p>现在使用插座1,代码如下：</p>\n" +
                "<pre><code class=\"lang-java\">Socket socket = new Socket1();\n" +
                "user.setSocket(socket);\n" +
                "user.userSocket;\n" +
                "</code></pre>\n" +
                "<p>这样会使接口Socket和实现类Socket1捆绑起来，即发生了耦合。如果想要使用其他插座，就要修改代码。比如现在换成使用插座2，代码如下：</p>\n" +
                "<pre><code class=\"lang-java\">Socket socket = new Socket2();\n" +
                "user.setSocket(socket);\n" +
                "user.userSocket;\n" +
                "</code></pre>\n" +
                "<p>可以看出，每一次更换插座都需要修改源代码。一个大型的互联网项目中的对象成千上万，修改源代码会使得项目难以维护。</p>\n" +
                "<p>用Spring IoC容器管理对象</p>\n" +
                "<p>现在，我们不用new的方式创建对象了，而是使用配置的方式，然后让Spring IoC容器自己通过配置去找到插座。先用一段XML描述插座和用户的引用插座1，代码如下：</p>\n" +
                "<pre><code class=\"lang-xml\">&lt;bean id=&quot;socket&quot; class=&quot;Socket1&quot;&gt;&lt;/bean&gt;\n" +
                "&lt;bean id=&quot;user&quot; class=&quot;User&quot;&gt;\n" +
                "    &lt;property name=&quot;socket&quot; ref=&quot;socket&quot;&gt;&lt;/property&gt;\n" +
                "&lt;/bean&gt;\n" +
                "</code></pre>\n" +
                "<p>当我们想要更换成使用插座2时，只需要修改上面的配置文件，就可以向用户注入插座2，代码如下：</p>\n" +
                "<pre><code class=\"lang-xml\">&lt;bean id=&quot;socket&quot; class=&quot;Socket2&quot;&gt;&lt;/bean&gt;\n" +
                "&lt;bean id=&quot;user&quot; class=&quot;User&quot;&gt;\n" +
                "    &lt;property name=&quot;socket&quot; ref=&quot;socket&quot;&gt;&lt;/property&gt;\n" +
                "&lt;/bean&gt;\n" +
                "</code></pre>\n" +
                "<p>这个时候Socket接口就可以不依赖于任何插座了，而是通过配置进行切换，如图所示：</p>\n" +
                "<p><img src=\"http://q8rsjstig.bkt.clouddn.com/note/Spring的控制反转.png\" alt=\"Spring的控制反转\"></p>\n" +
                "<p>图中的配置信息是“我要插座2”，相当于XML依赖关系配置，这个时候Spring IoC只会拿到插座2，然后通过国家插座标准接口注入给使用者，提供给使用者使用。</p>\n" +
                "<p>这样，你不需要去找资源，只要向Spring IoC容器描述所需资源，Spring IoC自己会找到你所需要的资源。</p>\n" +
                "<h3 id=\"h3-spring-aop\"><a name=\"Spring AOP\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Spring AOP</h3><p>订单超预算例子：</p>\n" +
                "<p><img src=\"http://q8rsjstig.bkt.clouddn.com/note/订单超预算.png\" alt=\"订单超预算\"></p>\n" +
                "<p>实线是订单提交的流程，需显示订单驳回的流程，影响他们的条件是预算超额，这是一个切面条件。</p>\n" +
                "<p>Spring AOP常用于数据库事务的编程，很多情况像上面的例子一样，我们在做完第一步数据库数据更新后，不知道下一步是否会成功，如果下一步失败，会使用数据库事务回滚功能去回滚事务，使得第一步的数据库操作更新也作废。Spring AOP实现了当Spring接收到了异常信息，就会回滚事务，不需要代码实现。伪代码如下：</p>\n" +
                "<pre><code class=\"lang-java\">/**\n" +
                "* Spring AOP处理订单伪代码\n" +
                "* @param order 订单\n" +
                "**/\n" +
                "private void proceed(Order order){\n" +
                "    //判断生产部门是否通过订单，数据库记录订单\n" +
                "    boolean pflag = productionDept.isPass(order);\n" +
                "    if(pflag){//如果生产部门通过进行财务部门审批\n" +
                "        if(financialDept.isOverBudget(order)){//财务审批是否超限\n" +
                "            throw new RuntimeException(&quot;预算超限！！！&quot;);\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>这里我们完全看不到数据库代码，也没有复杂的try…catch…finally…语句。有了Spring AOP，我们只需要关注业务代码，知道只要发生了异常，Spring会回滚事务就足够了。</p>\n" +
                "<h2 id=\"h2-mybatis\"><a name=\"MyBatis\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>MyBatis</h2><p>一个基于Java的持久层框架。提供自动映射、动态SQL、级联、缓存、注解、代码和SQL分离等特性。</p>\n" +
                "<h2 id=\"h2-hibernate\"><a name=\"Hibernate\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Hibernate</h2><p>完全面向POJO</p>\n" +
                "<p>Hibernate和Mybatis的区别</p>\n" +
                "<ul>\n" +
                "<li><p>Hibernate不需要接口和SQL，而Mybatis需要，因此Mybatis的工作量较大；</p>\n" +
                "</li><li><p>Hibernate性能较低，但在管理系统时代发挥巨大作用。而Mybatis性能较高，更适合于当今性能要求高、响应快的互联网时代。</p>\n" +
                "</li><li><p>Mybatis较Hibernate更加灵活，可以自由书写SQL、支持动态SQL、支持存储过程等</p>\n" +
                "</li></ul>\n" +
                "<h2 id=\"h2-spring-mvc\"><a name=\"Spring MVC\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Spring MVC</h2><p>结构层次清晰，与Spring的IoC和AOP无缝对接，成为了互联网时代的主流框架。</p>\n" +
                "<h2 id=\"h2-redis\"><a name=\"Redis\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Redis</h2><p>最为流行的NoSQL，将常用数据放在内存中，可以大幅度提高互联网系统性能。</p>\n";
        String result = HtmlUtil.removeTag(text);
        System.out.println(result);
    }
}
