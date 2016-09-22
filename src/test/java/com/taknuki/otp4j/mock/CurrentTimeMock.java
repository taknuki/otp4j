package com.taknuki.otp4j.mock;

import java.util.Date;

import mockit.Mock;
import mockit.MockUp;

public class CurrentTimeMock extends MockUp<System> {
	private final Date mockTime;

	public CurrentTimeMock(Date mockTime) {
		this.mockTime = mockTime;
	}

	@Mock
	public long currentTimeMillis() {
		return mockTime.getTime();
	}

}
