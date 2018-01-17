# spring boot resource file

- **程序结构**
```
├─main
│  ├─java
│  │  └─com
│  │      └─hf
│  │          └─controller
│  │                  ResourcesController.java
│  │
│  └─resources
│          application.properties
│          test.txt
│
└─test
    └─java
```

- **ResourcesController类**
```
package com.hf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@RestController
@EnableAutoConfiguration
public class ResourcesController {
    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/get-resources",method = RequestMethod.GET)
    public String getResources() throws IOException{
        Resource resource = resourceLoader.getResource("classpath:test.txt");
        String content="";
        try{
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                content = content + line;
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return "the content of resources: " + content;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourcesController.class,args);
    }
}

```

- **访问http://localhost:8080/get-resources**
```
the content of resources: hello test.text balabalabala
```

- **summary** <br>
just talk -->don't talk ,give me code.-->talk,and give me code.


 