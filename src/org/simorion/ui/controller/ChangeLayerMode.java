package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Change Layer Mode.
 * @author Karl Brown
 */
public class ChangeLayerMode extends DeviceMode {
	
	// FIXME: Replace with model.getCurrentLayerId()
	int layer = -1;
 
    public ChangeLayerMode(ModeMaster m) {
		super(m);
	}

	private ChangeLayerView instance = new ChangeLayerView();
     
    /**
     * Implementation of the View interface for the ChangeLayerView.
     * @author Karl Brown
     *
     */
    private class ChangeLayerView extends DefaultView {
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {    		
    		return isRowLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isRowLit(int x) {
    		
    		// If voice is -1, then no button has been selected.
    		if (layer == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return x - (layer % 16) == 0;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public String getLCDMessage() {
    		return model.getLCDDisplay();
    	}
         
    }
     
	/**
	 * {@inheritDoc}
	 */
    public View getView() {
        return instance;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		if(layer != -1 && layer <= 16) {
			model.setTopmostLayer(layer);
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		model.setLCDDisplay("Layer set to " + model.getCurrentLayerId());
		reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		layer = y;
		model.setLCDDisplay(Integer.toString(y));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void onChangedTo() {
		layer = model.getCurrentLayerId();
		model.setLCDDisplay("Change Layer Mode");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void reset() {
		layer = -1;
	}
     
}