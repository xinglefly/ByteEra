package com.zhicai.byteera.service.serversdk;

@SuppressWarnings("static-access")
public abstract  class IThread {
    private Thread m_thread;
    private boolean _run = false;

    public int start() {
        if (_run)
            return 0;

        if (_prepare() < 0) {
            System.out.println("Prepare fail!");
            return -1;
        }

        _run = true;

        m_thread = new Thread(new Runnable() {
			@Override
            public void run() {
                while (_run) {
                    _kernel();
                    try {
                        m_thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        m_thread.start();
        return 0;
    }

    public int stop() {
        if ( !_run )
            return -1;

        _run = false;

        try {
            m_thread.join(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _finish();
        return 0;
    }

    abstract int _prepare();
    abstract int _kernel();
    abstract void _finish();
}
