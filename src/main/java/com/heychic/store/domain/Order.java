package com.heychic.store.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="user_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date orderDate;
	private Date shippingDate;
	private String orderStatus;
	private BigDecimal orderTotal;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartItems;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Shipping shipping;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = true)
	private User employee;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Getter and Setter
	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}
	
}
