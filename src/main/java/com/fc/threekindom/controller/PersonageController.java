package com.fc.threekindom.controller;

import com.fc.threekindom.mappers.PersonageMapper;
import com.fc.threekindom.pojo.Personage;
import com.fc.threekindom.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/personage")
public class PersonageController {
    @Autowired(required = false)
    private PersonageMapper personageMapper;

    //创建新人物
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestParam("file") MultipartFile file, String personName, String life, String nativePlace, String nation) {
        System.out.println("ffffffffff");
        Map<String,Object> map=new HashMap<>();
        //参数判断
        if (file==null||"".equals(file)){
            map.put("msg","请选择上传文件");
            return map;
        }
        //判断文件大小
        if (file.getSize()>52428800){
            map.put("state",100);
            map.put("msg","文件大小超出50M");
            return map;
        }
//        //获取上传文件的原名
//        String filename = file.getOriginalFilename();
//        //从后往前找文件名的第一个"."
//      int start = filename.lastIndexOf(".");
        //得到文件的后缀
        String subffix = ".webp";
        //filename.substring(start);
        //使用UUID产生文件名前缀
        String preffix = UUID.randomUUID().toString();
        //新的文件名
        String newFileName=preffix+subffix;
        //文件存储在哪里

        File f= UploadUtils.getPerDirFile();;

        File filePath=new File(f.getAbsolutePath() +File.separator+newFileName);

        try {   //实现上传到服务器
            file.transferTo(filePath);


            //头像路径
            String personImage="/upload/personImage/"+newFileName;
            Personage personage=new Personage();
            personage.setLife(life);
            personage.setName(personName);
            personage.setNation(nation);
            personage.setNativePlace(nativePlace);
            personage.setPersonImage(personImage);
            int row = personageMapper.addNewPerson(personage);

            if (row>=1){
                map.put("state","200");
                map.put("msg","上传成功");
                return map;
            }else {
                map.put("state","100");
                map.put("msg","上传失败,请重试");
                return map;
            }


        } catch (IOException e) {
            e.printStackTrace();
            map.put("state","100");
            map.put("msg","上传失败");
            return map;
        }


    }
//通过id查找人物信息
    @PostMapping("/findPersonById")
    @ResponseBody
    public Map<String,Object> findPersonById(String id){
        Map<String,Object> map=new HashMap<>();
        Personage person = personageMapper.findPersonById(Integer.parseInt(id));
        map.put("person",person);
        map.put("state",200);
        return map;
    }
    //带图片修改信息
    @PostMapping("/modify")
    @ResponseBody
    public Map<String,Object> modify(@RequestParam("file") MultipartFile file, String personName, String life, String nativePlace, String nation,String id){
        Map<String,Object> map=new HashMap<>();
        //参数判断
        if (file==null||"".equals(file)){
            map.put("msg","请选择上传文件");
            return map;
        }
        //判断文件大小
        if (file.getSize()>52428800){
            map.put("state",100);
            map.put("msg","文件大小超出50M");
            return map;
        }
//        //获取上传文件的原名
//        String filename = file.getOriginalFilename();
//        //从后往前找文件名的第一个"."
//      int start = filename.lastIndexOf(".");
        //得到文件的后缀
        String subffix = ".webp";
        //filename.substring(start);
        //使用UUID产生文件名前缀
        String preffix = UUID.randomUUID().toString();
        //新的文件名
        String newFileName=preffix+subffix;
        //文件存储在哪里

        File f= UploadUtils.getPerDirFile();;

        File filePath=new File(f.getAbsolutePath() +File.separator+newFileName);

        try {   //实现上传到服务器
            file.transferTo(filePath);


            //头像路径
            String personImage="/upload/personImage/"+newFileName;
            Personage personage=new Personage();
            personage.setLife(life);
            personage.setName(personName);
            personage.setNation(nation);
            personage.setNativePlace(nativePlace);
            personage.setPersonImage(personImage);
            personage.setId(Integer.parseInt(id));
            System.out.println(personage+"hhhhhh");
            int row = personageMapper.modifyInfo(personage);

            if (row>=1){
                map.put("state","200");
                map.put("msg","修改成功");
                return map;
            }else {
                map.put("state","100");
                map.put("msg","修改失败,请重试");
                return map;
            }


        } catch (IOException e) {
            e.printStackTrace();
            map.put("state","100");
            map.put("msg","修改失败");
            return map;
        }

    }
    //不带图片修改信息
    @PostMapping("/modifyWithoutPic")
    @ResponseBody
    public Map<String,Object> modifyWithoutPic( String personName, String life, String nativePlace, String nation,String id){
        Map<String,Object> map=new HashMap<>();

            Personage personage=new Personage();
            personage.setLife(life);
            personage.setName(personName);
            personage.setNation(nation);
            personage.setNativePlace(nativePlace);
            personage.setId(Integer.parseInt(id));
            int row = personageMapper.modifyInfoWithoutPic(personage);

            if (row>=1){
                map.put("state","200");
                map.put("msg","修改成功");
                return map;
            }else {
                map.put("state","100");
                map.put("msg","修改失败,请重试");
                return map;
            }

    }
    //通过id删除人物
    @PostMapping("/deletePersonById")
    @ResponseBody
    public Map<String,Object> deletePersonById(String id){
        Map<String,Object> map=new HashMap<>();
        int i = personageMapper.deletePersonById(Integer.parseInt(id));
        if (i>=1){
            map.put("state","200");
            map.put("msg","删除成功");
            return map;
        }else {
            map.put("state","100");
            map.put("msg","删除失败,请重试");
            return map;
        }
    }
    //去图鉴人物信息界面
    @GetMapping("/toPersonagePageById")
    public String toPersonagePage(String id, Model model){
        Personage person = personageMapper.findPersonById(Integer.parseInt(id));
        model.addAttribute("personage",person);


        return "Personage";
    }


}
