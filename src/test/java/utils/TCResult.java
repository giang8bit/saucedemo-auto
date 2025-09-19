package utils;

public class TCResult {

	public Boolean Result = true;
	public String Message = "\r\n";

	public Boolean GetResult() {
		return Result;
	}

	public String GetMessage() {
		return Message;
	}

	public void SetResult(Boolean pResult) {
		this.Result = pResult;
	}

	/************* ✨ Windsurf Command ⭐ *************/
	/**
	 * Set the message associated with this TCResult.
	 * 
	 * @param pMessage the message to be set
	 */
	/******* e05ed91c-b796-42eb-83bd-3ff7beeb15a8 *******/
	public void SetMessage(String pMessage) {
		this.Message += pMessage + ".\r\n";
	}
}
