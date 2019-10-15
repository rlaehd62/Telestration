package DTO.Response;

public class CreateRoomResponse implements GamePacketResponse
{
    private RoomResponse response;
    private boolean isAccepted;

    public CreateRoomResponse(RoomResponse response)
    {
        this.response = response;
    }

    public void setAccepted(boolean accepted)
    {
        isAccepted = accepted;
    }

    public boolean isAccepted()
    {
        return isAccepted;
    }

    public RoomResponse getResponse()
    {
        return response;
    }
}
