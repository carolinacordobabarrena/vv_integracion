package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {

	/**
	 * RELLENAR POR EL ALUMNO
	 */
	@Mock
	private User user;
	@Mock
	private static AuthDAO authDAO;
	@Mock
	private static GenericDAO genericDAO;
	@Mock
	private static Object filter;
	@InjectMocks
	SystemManager systemManager;
	/*
	*
	* this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.roles = roles;
	* */
	@Test
	public void testUsuarioValidoStartRemoteSystem()  throws Exception{
		User user = new User("14","Fernando","Magic","El nano ae", new ArrayList<>());
		systemManager = new SystemManager(authDAO,genericDAO);
		//when(systemManager.startRemoteSystem("14","35")).thenReturn(new ArrayList<>());
		ArrayList<String> arrayListVacia = new ArrayList();
		when(authDAO.getAuthData("14")).thenReturn(user);
		systemManager.startRemoteSystem("14","35");


		when(genericDAO.getSomeData(user,"35")).thenReturn(Collections.singleton(arrayListVacia));
		//when(systemManager.startRemoteSystem("14","35")).thenReturn(Collections.singleton(arrayListVacia));
		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).getSomeData(user,"where id=" +"35");
	}
	/*
	* Cuando (ejecuta algo) ---> Devuelves X
	* verificar que esto se cumple con la llamada al metodo
	* */
	@Test
	public void testUsuarioValidoStartRemoteSystem1()  throws Exception{
		User user = new User("14","Fernando","Magic","El nano ae", new ArrayList<>());

		SystemManager systemManager = new SystemManager(authDAO,genericDAO);
		when(systemManager.startRemoteSystem("14","35")).thenReturn(new ArrayList<>());
		//when(authDAO.getAuthData("14")).thenReturn(user);
		systemManager.startRemoteSystem("14","35");
		//verify(authDAO, times(2)).getAuthData("14");
		verify(genericDAO,times(1)).getSomeData(user,"14");
		//verify(vehiculo, times(1)).pisarFreno();

	}
}
