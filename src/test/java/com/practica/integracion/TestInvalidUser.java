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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	/**
	 * RELLENAR POR EL ALUMNO
	 */

	@Mock
	private static AuthDAO authDAO;
	@Mock
	private static GenericDAO genericDAO;
	@InjectMocks
	SystemManager systemManager;

	private static User user = null;

	@Test
	public void testStartRemoteSystem() throws OperationNotSupportedException {
		when(genericDAO.getSomeData(user,"where id=35")).thenThrow(new OperationNotSupportedException());
		when(authDAO.getAuthData("14")).thenReturn(user);

		Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.startRemoteSystem("14","35");
		});

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(0)).getSomeData(user,"35");

	}

	@Test
	public void testStopRemoteSystem() throws OperationNotSupportedException {
		when(genericDAO.getSomeData(user,"where id=35")).thenThrow(new OperationNotSupportedException());
		when(authDAO.getAuthData("14")).thenReturn(user);

		Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.startRemoteSystem("14","35");
		});

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(0)).getSomeData(user,"35");

	}

	@Test
	public void testAddRemoteSystemInvalido() throws OperationNotSupportedException {
		when(genericDAO.updateSomeData(user,"35")).thenThrow(new OperationNotSupportedException());
		when(authDAO.getAuthData("14")).thenReturn(user);

		Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.addRemoteSystem("14","35");
		});

		verify(authDAO, times(1)).getAuthData("14");
		verify(genericDAO,times(1)).updateSomeData(user,"35");
	}

	@Test
	public void testDeleteRemoteSystemInvalido() throws OperationNotSupportedException {
		when(genericDAO.deleteSomeData(user,"35")).thenThrow(new OperationNotSupportedException());
		lenient().when(authDAO.getAuthData("14")).thenReturn(user);

		Assertions.assertThrows(SystemManagerException.class, () -> {
			systemManager.deleteRemoteSystem("14","35");
		});

		verify(authDAO, times(0)).getAuthData("14");
		verify(genericDAO,times(1)).deleteSomeData(user,"35");
	}

}
