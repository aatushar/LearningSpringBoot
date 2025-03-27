package com.sazzad.erpReport.service.duePayment;

import net.sf.jasperreports.engine.JRException;

public interface ProgramDuePaymentService {
    byte[] generateReport() throws JRException;
}
