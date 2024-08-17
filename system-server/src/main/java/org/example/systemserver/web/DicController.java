package org.example.systemserver.web;


import org.example.systemserver.SystemServerApplication;
import org.example.systemserver.domain.TActivity;
import org.example.systemserver.domain.TDicValue;
import org.example.systemserver.domain.TProduct;
import org.example.systemserver.result.DicEnum;
import org.example.systemserver.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicController {

    @GetMapping(value = "/api/dicvalue/{typeCode}")
    public R dicData(@PathVariable(value = "typeCode") String typeCode) {
        if (typeCode.equals(DicEnum.ACTIVITY.getCode())) { //activity
            List<TActivity> tActivityList = (List<TActivity>) SystemServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        } else if (typeCode.equals(DicEnum.PRODUCT.getCode())) {
            List<TProduct> tProductList = (List<TProduct>)SystemServerApplication.cacheMap.get(typeCode);
            return R.OK(tProductList);
        } else {
            List<TDicValue> tDicValueList = (List<TDicValue>)SystemServerApplication.cacheMap.get(typeCode);
            return R.OK(tDicValueList);
        }
    }
}
