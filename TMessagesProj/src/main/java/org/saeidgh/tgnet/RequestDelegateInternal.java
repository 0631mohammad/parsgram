package org.saeidgh.tgnet;

public interface RequestDelegateInternal {
    void run(int response, int errorCode, String errorText);
}
