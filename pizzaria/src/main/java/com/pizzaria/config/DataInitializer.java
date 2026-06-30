package com.pizzaria.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pizzaria.entity.Admin;
import com.pizzaria.entity.Bebida;
import com.pizzaria.entity.Pizza;
import com.pizzaria.repository.AdminRepository;
import com.pizzaria.repository.BebidaRepository;
import com.pizzaria.repository.PizzaRepository;
import com.pizzaria.util.SenhaUtil;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Override
    public void run(String... args) {
        if (!adminRepository.existsByUsuario("admin")) {
            Admin admin = new Admin();
            admin.setUsuario("admin");
            admin.setSenha(SenhaUtil.criptografar("admin123"));
            adminRepository.save(admin);
        }

        garantirPizza("Portuguesa",
                "Uma receita tradicional e muito bem recheada, que equilibra perfeitamente o sabor do presunto, ovos, cebola, ervilhas e azeitonas.",
                "38.90",
                "portuguesa");
        garantirPizza("Calabresa",
                "O grande clássico das pizzarias, combinando fatias defumadas e selecionadas de calabresa com cebola fresca sobre nossa massa artesanal.",
                "36.90",
                "calabreza");
        garantirPizza("Quatro Queijos",
                "Uma rica e intensa sinfonia de texturas, combinando a perfeita harmonia entre queijos nobres derretidos no forno a lenha.",
                "42.90",
                "quatroqueijos");
        garantirPizza("Chocolate Preto",
                "A sobremesa perfeita em forma de pizza, coberta com uma camada generosa de chocolate meio amargo derretido e cremoso.",
                "34.90",
                "chocolate");
        garantirPizza("Chocolate Branco com Morangos",
                "O contraste irresistível do chocolate branco premium derretido com o frescor e a leve acidez de morangos selecionados.",
                "39.90",
                "chocolate branco");
        garantirPizza("Charge",
                "Uma tentação para os amantes de doce, unindo o sabor marcante do chocolate com a textura do amendoim e a cremosidade do caramelo.",
                "37.90",
                "charge");

        garantirBebida("Água", "4.00", "agua");
        garantirBebida("Coca-Cola", "7.00", "coca");
        garantirBebida("Suco de Uva", "8.00", "suco");
    }

    private void garantirPizza(String nome, String descricao, String valor, String imagem) {
        if (!pizzaRepository.existsByNome(nome)) {
            pizzaRepository.save(criarPizza(nome, descricao, valor, imagem));
        }
    }

    private void garantirBebida(String nome, String valor, String imagem) {
        if (!bebidaRepository.existsByNome(nome)) {
            bebidaRepository.save(criarBebida(nome, valor, imagem));
        }
    }

    private Pizza criarPizza(String nome, String descricao, String valor, String imagem) {
        Pizza pizza = new Pizza();
        pizza.setNome(nome);
        pizza.setDescricao(descricao);
        pizza.setValor(new BigDecimal(valor));
        pizza.setAtivo(true);
        pizza.setImagem(imagem);
        return pizza;
    }

    private Bebida criarBebida(String nome, String valor, String imagem) {
        Bebida bebida = new Bebida();
        bebida.setNome(nome);
        bebida.setValor(new BigDecimal(valor));
        bebida.setAtivo(true);
        bebida.setImagem(imagem);
        return bebida;
    }
}
