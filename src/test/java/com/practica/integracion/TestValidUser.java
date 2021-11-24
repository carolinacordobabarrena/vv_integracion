package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Assertions;
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
	private static AuthDAO authDAO;
	@Mock
	private static GenericDAO genericDAO;
	@InjectMocks
	SystemManager systemManager;

	private static User user = new User("14","Fernando","Magic","El nano ae", new ArrayList<>());

	@Test
	public void testStartRemoteSystem()  throws Exception{
		Collection<Object> arrayListVacia = new ArrayList();

		when(genericDAO.getSomeData(user,"where id=35")).thenReturn(arrayListVacia);
		when(authDAO.getAuthData("14")).thenReturn(user);

		Collection<Object> aux = systemManager.startRemoteSystem("14","35");

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).getSomeData(user,"where id=" +"35");
		assertEquals(aux,arrayListVacia);
	}

	@Test
	public void testStopRemoteSystem()  throws Exception{
		Collection<Object> arrayListVacia = new ArrayList();

		when(genericDAO.getSomeData(user,"where id=35")).thenReturn(arrayListVacia);
		when(authDAO.getAuthData("14")).thenReturn(user);

		Collection<Object> aux = systemManager.stopRemoteSystem("14","35");

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).getSomeData(user,"where id=" +"35");
		assertEquals(aux,arrayListVacia);

	}

	@Test
	public void testAddRemoteSystemValido() throws OperationNotSupportedException, SystemManagerException {
		when(genericDAO.updateSomeData(user,"35")).thenReturn(true);
		when(authDAO.getAuthData("14")).thenReturn(user);

		systemManager.addRemoteSystem("14","35");

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).updateSomeData(user,"35");
	}

	@Test
	public void testAddRemoteSystemInvalido() throws OperationNotSupportedException, SystemManagerException {
		when(genericDAO.updateSomeData(user,"35")).thenReturn(false);
		when(authDAO.getAuthData("14")).thenReturn(user);

		Exception ex = Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.addRemoteSystem("14","35");
		});

		assertEquals(ex.getMessage(),"cannot add remote");
		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).updateSomeData(user,"35");
	}
	//El metodo no utiliza el id pasado por parametro que hacemos :D
	@Test
	public void testDeleteRemoteSystemValido() throws SystemManagerException, OperationNotSupportedException {
		when(genericDAO.deleteSomeData(user,"35")).thenReturn(true);
		when(authDAO.getAuthData("14")).thenReturn(user);

		systemManager.deleteRemoteSystem("14","35");

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).deleteSomeData(user,"35");
	}

	@Test
	public void testDeleteRemoteSystemInvalido() throws SystemManagerException, OperationNotSupportedException {
		when(genericDAO.deleteSomeData(user,"35")).thenReturn(false);
		when(authDAO.getAuthData("14")).thenReturn(user);

		Exception ex = Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.deleteRemoteSystem("14","35");
		});

		assertEquals(ex.getMessage(),"cannot delete remote: does remote exists?");
		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).deleteSomeData(user,"35");
	}
}
