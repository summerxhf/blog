# spring @Controller  @RestController distinction

spring @Controller ע�������һ��������Ϊ��������ɫ�����ڵ�. ʹ��spring ���Բ���ȥ�̳��κο�����, Ҳ����ȥʵ��servlet API, ��Ȼ,�����Ҫ���ǿ���ʹ���κ���servlet��ص����Ժ���ʩ, spirng ��װ��ԭʼ��servlet.

spring 4���������@RestController, ��ô@Controller ��@RestController ����?

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

@RestController ע���൱��@ResponseBody + @Controller

��������������ͬ��,���Ķ���ͬһ������
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
���Ծ����ô�����Щ

