package com.camelotinteractive.game.hugoandcat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.camelotinteractive.game.escape.Dondtwel;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGeneratorTestCase;

public class TestTryyourluck extends TicketDataGeneratorTestCase{

	@Before
	public void setUp() throws Exception {
		setUpMocks(new Tryyourluck_old());
	}

	@Test
	public void testGetTicketData() throws TicketDataException {
		System.out.println(getTicketData());
	}}
