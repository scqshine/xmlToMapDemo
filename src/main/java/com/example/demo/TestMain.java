package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * testMain <br>
 * <功能详细描述>
 *
 * @author: IT讲坛
 * @see [相关类/方法]（可选）
 * @since 1.0.0
 **/
public class TestMain {
    public static void main(String[] args) {
        String res="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<service><SYS_HEAD><SvcCd>60012012</SvcCd><SvcScn>01</SvcScn><GlobalSrlNo>G0311231020123000000000000200</GlobalSrlNo><CnsmrSrlNo>031312323017485400000001</CnsmrSrlNo><PvdrSrlNo>030103123201230174813556911</PvdrSrlNo><OrigCnsmrSysNo></OrigCnsmrSysNo><CnsmrSysNo>031101</CnsmrSysNo><TrgtSysNo></TrgtSysNo><RespDate>20201230</RespDate><RespTime>174852</RespTime><ChnlTp>Z10</ChnlTp><TxnRetSt>S</TxnRetSt><RetInfArray><RetCode>000000</RetCode><RetMsg>success</RetMsg></RetInfArray></SYS_HEAD><APP_HEAD><InstNo></InstNo><UsrNo></UsrNo><UsrPswd></UsrPswd><CtfctnInfo></CtfctnInfo></APP_HEAD><BODY><bd><TmlCd>1</TmlCd><OrigTmlCd></OrigTmlCd><CnsmrSrvInd></CnsmrSrvInd><OrigCnsmrSrvInd></OrigCnsmrSrvInd><TrgtSrvInd></TrgtSrvInd></bd><bd><TmlCd>2</TmlCd><OrigTmlCd></OrigTmlCd><CnsmrSrvInd></CnsmrSrvInd><OrigCnsmrSrvInd></OrigCnsmrSrvInd><TrgtSrvInd></TrgtSrvInd></bd></BODY></service>";
        Map<String, Object> stringObjectMap = parseXmlToMap(res);
        System.out.println(JSON.toJSONString(stringObjectMap));


    }



    /**
     * xml格式字符串转换为map
     *
     * @param xml xml字符串
     */
    public static Map<String, Object> parseXmlToMap(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            System.out.println("获取xml异常");
        }
        Map<String, Object> map = new HashMap<>();
        if (null == doc) {
            return map;
        }
        // 获取根元素
        Element rootElement = doc.getRootElement();
        mulXmlToMap(rootElement,map);
        return map;
    }

    private static void mulXmlToMap(Element element, Map<String, Object> outmap) {
        // 获取根元素下的子元素列表
        List<Element> list = element.elements();
        int size = list.size();
        if (size == 0) {
            // 如果该层没有子元素,则直接将其存储进map中
            outmap.put(element.getName(), element.getTextTrim());
        } else {
            // innermap用于存储子元素的属性名和属性值
            Map<String, Object> innermap = new HashMap<>();
            List<Map<String,Object>> innerMapList=new ArrayList<>();
            // 递归遍历子元素
            list.forEach(childElement -> mulXmlToMap(childElement, innermap));
            innerMapList.add(innermap);
            //对于多层的,要判断是否已经包含该key,以防多层的情况下元素被覆盖.
            if (outmap.containsKey(element.getName())){
                List<Map<String,Object>> innerMl = (List<Map<String, Object>>) outmap.get(element.getName());
                innerMl.add(innermap);
                outmap.put(element.getName(), innerMl);
            }else {
                outmap.put(element.getName(), innerMapList);
            }
        }
    }

}
