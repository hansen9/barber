package com.movaintelligence.barber.sales.domain.repository;

import com.movaintelligence.barber.sales.domain.entity.Sale;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class SalesPlainSqlRepository {

    private final EntityManager em;

    public SalesPlainSqlRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Sale createSale(Sale sale) {

        // Insert ke DB
        var sql = "INSERT INTO sale (order_id, total_price, sale_date) VALUES (?, ?, ?)";
        var query = em.createNativeQuery(sql);
        query.setParameter(1, sale.getOrder().getId());
        query.setParameter(2, sale.getAmount());
        query.setParameter(3, sale.getDate());
        query.executeUpdate();

        // Retrieve the generated ID
        sql = "SELECT currval('sale_id_seq')";
        var idQuery = em.createNativeQuery(sql);
        Long generatedId = (Long) idQuery.getSingleResult();

        // Pasangkan ID ke objek entity sale
        sale.setId(generatedId);

        return sale;
    }
}
