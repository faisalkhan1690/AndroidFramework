package com.geekandroidframework.ui;

public interface IScreen {

    void getData(final int actionID);
    /**
     * Subclass should over-ride this method to update the UI with response. <br/>
     * Subclass should note that it might being called from non-UI thread.
     *
     * @param serviceResponse
     */

    void updateUi(final boolean status, final int actionID, final Object serviceResponse);

    /**
     * Subclass should over-ride this method to update the UI on event <br/>
     * Caller should note that it should be called only from UI thread.
     *
     * @param eventId
     * @param eventData
     * @deprecated
     */
    void onEvent(int eventId, Object eventData);



}
