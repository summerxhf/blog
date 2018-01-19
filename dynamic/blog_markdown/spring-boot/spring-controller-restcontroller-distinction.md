# spring @Controller  @RestController distinction

spring @Controller 注解表明了一个类是作为控制器角色而存在的. 使用spring 可以不用去继承任何控制类, 也不用去实现servlet API, 当然,如果需要我们可以使用任何与servlet相关的特性和设施, spirng 封装了原始的servlet.

spring 4中新添加了@RestController, 那么@Controller 和@RestController 区别?

<table>
 <thead> 
  <tr> 
   <th align="left">Annotations</th> 
   <th align="left">Description</th> 
  </tr> 
 </thead>
 <tbody> 
  <tr> 
   <td align="left">@Controller</td> 
   <td align="left">is common annotation in Spring MVC. It is used to mark classes as Spring MVC Controller.</td> 
  </tr> 
  <tr> 
   <td align="left">@RestController</td> 
   <td align="left">is a new annotation in Spring4. It is equivalent to using @Controller and @ResponseBody together.</td> 
  </tr> 
 </tbody>
</table>

@RestController 注解相当于@ResponseBody + @Controller

所以这两个是相同的,做的而是同一件事情
```
    @Controller
    @ResponseBody
    public class SimpleController { }
   
```

```
    @RestController
    public class SimpleController { }
```
- summay
可以尽量让代码简洁些

