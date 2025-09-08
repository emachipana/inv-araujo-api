package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.VitroOrder;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

public interface VitroOrderRepository extends JpaRepository<VitroOrder, Long>, JpaSpecificationExecutor<VitroOrder> {
    @Query("SELECT o FROM VitroOrder o WHERE " +
    "(COALESCE(:search, '') = '' OR " +
    "LOWER(o.client.document) LIKE LOWER(concat('%', :search, '%')) OR " +
    "LOWER(o.client.rsocial) LIKE LOWER(concat('%', :search, '%'))) " +
    "AND (:pending IS NULL OR o.pending <= :pending) " +
    "AND (:isReady IS NULL OR o.isReady = :isReady) " +
    "AND (:shipType IS NULL OR o.shippingType = :shipType) " +
    "AND (:status IS NULL OR o.status = :status)")
    Page<VitroOrder> searchOrders(
        @Param("search") String search,
        @Param("pending") Double pending,
        @Param("isReady") Boolean isReady,
        @Param("shipType") ShippingType shipType,
        @Param("status") Status status,
        Pageable pageable
    );

    Page<VitroOrder> findByClient_DocumentContainingIgnoreCaseOrClient_RsocialContainingIgnoreCaseOrStatusOrIsReadyOrShippingType(
        String document,
        String rsocial,
        Status status,
        Boolean isReady,
        ShippingType shipType,
        Pageable pageable
    );

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN o.status IN ('ENTREGADO', 'ENVIADO') THEN 1 ELSE 0 END), 0), 
            COALESCE(SUM(CASE WHEN o.status IN ('PENDIENTE', 'PAGADO') THEN 1 ELSE 0 END), 0) 
        FROM VitroOrder o
    """)
    List<Object[]> countOrdersByGroupedStatus();

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN o.shippingType = 'ENVIO_AGENCIA' AND o.status = 'PENDIENTE' AND o.isReady = true AND o.pending <= 0 THEN 1 ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN o.shippingType = 'RECOJO_ALMACEN' AND o.status = 'PENDIENTE' AND o.isReady = true AND o.pending <= 0 THEN 1 ELSE 0 END), 0)
        FROM VitroOrder o
    """)
    List<Object[]> countPaidOrdersByShippingType();

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

    Long countByClientId(Long clientId);
}
