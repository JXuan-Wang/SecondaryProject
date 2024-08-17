package org.example.systemserver.service;

import com.github.pagehelper.PageInfo;
import org.example.systemserver.domain.TCustomer;
import org.example.systemserver.query.CustomerQuery;
import org.example.systemserver.result.CustomerExcel;

import java.util.List;

public interface CustomerService {
    Boolean convertCustomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomerByPage(Integer current);

    List<CustomerExcel> getCustomerByExcel(List<String> idList);
}
