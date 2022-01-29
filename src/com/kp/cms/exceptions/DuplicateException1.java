	package com.kp.cms.exceptions;
	
	public class DuplicateException1 extends BusinessException {
		private static final long serialVersionUID = 1L;

		public DuplicateException1() {
		}

		public DuplicateException1(String msg) {
			super(msg);
		}

		public DuplicateException1(Exception e) {
			super(e);
		}
	}
