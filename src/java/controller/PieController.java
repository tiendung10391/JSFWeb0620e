/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Chart;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.PieModel;
import org.primefaces.model.chart.PieChartModel;
import util.MessagesUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
@ManagedBean
@ViewScoped
public class PieController {

    private PieChartModel pieModel1;
    private PieModel pieModel;
    private List<Chart> lstChart;

    /**
     * Creates a new instance of PieController
     */
    public PieController() {
        try {
            pieModel = new PieModel();
            lstChart = pieModel.getListPieChart();
            pieModel1 = new PieChartModel();

            if(lstChart != null && !lstChart.isEmpty()){
                for(Chart chart : lstChart){
                    pieModel1.set(chart.getName(), chart.getValue());
                }
            }

            pieModel1.setTitle("Biểu tổng lương của phòng");
            pieModel1.setLegendPosition("w");
            pieModel1.setShadow(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.getMessage());
        }

    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

}
