package com.system.expenseTracker.service.other;

import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.repo.ExpenseRepo;
import com.system.expenseTracker.service.ExpenseService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportService {
    private final ExpenseService expenseService;
    @Autowired
    public ReportService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    public byte[] exportReport() throws FileNotFoundException, JRException {
        List<ExpenseResponseDto> expenseList = expenseService.getAllExpenses();
        //load a file and compile it
        File file = ResourceUtils.getFile("classpath:expense.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(expenseList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Created By","Expense Tracking System");
        JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print,baos);
        return baos.toByteArray();
    }
}
