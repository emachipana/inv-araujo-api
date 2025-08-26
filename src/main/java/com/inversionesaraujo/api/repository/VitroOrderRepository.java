package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.VitroOrder;

public interface VitroOrderRepository extends JpaRepository<VitroOrder, Long>, JpaSpecificationExecutor<VitroOrder> {
    Page<VitroOrder> 
        findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
			String department,
			String city,
			String rsocial,
            Pageable pageable
        );

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN o.status IN ('ENTREGADO', 'ENVIADO') THEN 1 ELSE 0 END), 0), 
            COALESCE(SUM(CASE WHEN o.status IN ('PENDIENTE', 'PAGADO') THEN 1 ELSE 0 END), 0) 
        FROM VitroOrder o
    """)
    List<Object[]> countOrdersByGroupedStatus();

    @Query("SELECT COUNT(o) " +
       "FROM VitroOrder o " +
       "WHERE o.status = 'PENDIENTE' " +
       "AND FUNCTION('DATE', o.finishDate) = CURRENT_DATE")
	Long totalDeliver();

    @Query("SELECT COALESCE(SUM(ov.quantity), 0) " +
           "FROM OrderVariety ov " +
           "WHERE YEAR(ov.vitroOrder.finishDate) = :year " +
           "AND MONTH(ov.vitroOrder.finishDate) = :month " +
           "AND ov.vitroOrder.isReady = false")
    Integer sumCantidadByMonth(@Param("year") Integer year, @Param("month") Integer month);
    
    @Query("SELECT DISTINCT YEAR(vo.finishDate) as year, MONTH(vo.finishDate) as month " +
           "FROM VitroOrder vo " +
           "JOIN vo.items ov " +
           "WHERE vo.isReady = false " +
           "ORDER BY year, month")
    List<Object[]> findMonthsWithOrders();
    
    @Query("SELECT ov.variety.tuber.name as tuberName, ov.variety.name as varietyName, SUM(ov.quantity) as totalQuantity " +
           "FROM OrderVariety ov " +
           "WHERE YEAR(ov.vitroOrder.finishDate) = :year " +
           "AND MONTH(ov.vitroOrder.finishDate) = :month " +
           "AND ov.vitroOrder.isReady = false " +
           "GROUP BY ov.variety.tuber.name, ov.variety.name")
    List<Object[]> findVarietyQuantitiesByMonth(@Param("year") Integer year, @Param("month") Integer month);
}
