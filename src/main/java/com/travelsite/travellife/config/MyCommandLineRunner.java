package com.travelsite.travellife.config;

import com.travelsite.travellife.dao.PagesecRepository;
import com.travelsite.travellife.po.PAGESEC;
import com.travelsite.travellife.utils.MD5FileUtil;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @PersistenceContext //注入实体管理器,执行持久化操作
    EntityManager entityManager;

    @Autowired
    PagesecRepository pagesecRepository;

    @Override
    public void run(String... args){
        System.out.println("获取并复制要防篡改的网页资源文件");
        URL uri = ClassLoader.getSystemResource("");
        String classes = URLDecoder.decode(uri.getPath());
        String sql = "select * from pagesec where status='开启'";
        Query query = entityManager.createNativeQuery(sql, PAGESEC.class);
        List<PAGESEC> resultList = query.getResultList();

        for(PAGESEC p:resultList){
            String path = p.getPath();
            if(!path.startsWith("/")&&!path.startsWith("\\")){
                path = "\\"+path;
            }
            System.out.println("复制资源文件【"+path+"】...正在复制");
            File htmlFile = new File(classes + "templates" + path);
            if(!htmlFile.exists()){
                System.err.println("资源文件【"+path+"】不存在，请检查路径是否正确");
                continue;
            }
            try {
                String md5str = MD5FileUtil.getFileMD5String(htmlFile);
                p.setMd5str(md5str);
                pagesecRepository.save(p);
                String pageDir = classes+"com\\travelsite\\travellife\\page";
                FileUtil.copyFile(htmlFile,new File(pageDir+path));
                System.out.println("资源文件【"+path+"】...复制完成");
            } catch (IOException e) {
                System.err.println("复制资源文件【"+path+"】发生异常...");
                e.printStackTrace();
            }
        }
    }
}
