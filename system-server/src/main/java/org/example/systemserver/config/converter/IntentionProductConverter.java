package org.example.systemserver.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.example.systemserver.SystemServerApplication;
import org.example.systemserver.domain.TProduct;
import org.example.systemserver.result.DicEnum;

import java.util.List;

/**
 * 意向产品的转换器
 *
 * Excel中的比亚迪e2   -->  java中的 2
 * 秦PLUS EV  -->  7
 */
public class IntentionProductConverter implements Converter<Integer> {

    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的 “比亚迪e2”  ----> Java类中是 2
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //cellData是Excel中读取到的数据，是“比亚迪e2”、“秦PLUS EV”
        String cellIntentionProductName = cellData.getStringValue();

        List<TProduct> tDicValueList = (List<TProduct>) SystemServerApplication.cacheMap.get(DicEnum.PRODUCT.getCode());
        for (TProduct tProduct : tDicValueList) {
            Integer id  = tProduct.getId();
            String name = tProduct.getName();

            if (cellIntentionProductName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
