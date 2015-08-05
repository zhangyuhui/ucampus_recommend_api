package com.moviedemo.tools

class ManualResetEvent(@volatile var isSet: Boolean = false) {
	private val monitor = new Object()

	def waitOne(): Boolean = {
		monitor.synchronized({
			if (!isSet)
				monitor.wait()
			isSet
		})
	}

	def waitOne(timeout: Int) : Boolean = {
		monitor.synchronized({
			if (!isSet)
				monitor.wait(timeout)
			isSet
		})
	}

	def set(autoReset: Boolean = false) {
		monitor.synchronized({
			isSet = true
			monitor.notifyAll()
		})
		if (autoReset)
			reset()
	}

	def setAndThrow(ex: Throwable, autoReset: Boolean = false) = {
		set()
		throw ex
	}

	def reset() {
		monitor.synchronized({
			isSet = false
		})
	}
}
