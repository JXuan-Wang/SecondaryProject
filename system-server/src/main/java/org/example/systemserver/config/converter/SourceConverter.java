package org.example.systemserver.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.example.systemserver.SystemServerApplication;
import org.example.systemserver.domain.TDicValue;
import org.example.systemserver.result.DicEnum;

import java.util.List;

/**
 * 线索来源的转换器
 *
 * Excel中的 “车展会”  ----> Java类中是 3
 * Excel中的 “网络广告”  ----> Java类中是 16
 */
public class SourceConverter implements Converter<Integer> {

    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的 “车展会”  ----> Java类中是 3
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //cellData是Excel中读取到的数据，是“车展会”、“网络广告”
        String cellSourceName = cellData.getStringValue();

        List<TDicValue> tDicValueList = (List<TDicValue>) SystemServerApplication.cacheMap.get(DicEnum.SOURCE.getCode());
        for (TDicValue tDicValue : tDicValueList) {
            Integer id  = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if (cellSourceName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
