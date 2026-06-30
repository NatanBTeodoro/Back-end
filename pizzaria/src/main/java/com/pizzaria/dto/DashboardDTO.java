package com.pizzaria.dto;

public class DashboardDTO {

    private long totalClientes;
    private long totalPedidos;
    private long pedidosHoje;

    public DashboardDTO() {
    }

    public DashboardDTO(long totalClientes, long totalPedidos, long pedidosHoje) {
        this.totalClientes = totalClientes;
        this.totalPedidos = totalPedidos;
        this.pedidosHoje = pedidosHoje;
    }

    public long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public long getPedidosHoje() {
        return pedidosHoje;
    }

    public void setPedidosHoje(long pedidosHoje) {
        this.pedidosHoje = pedidosHoje;
    }
}
