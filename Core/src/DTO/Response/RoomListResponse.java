package DTO.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomListResponse implements GamePacketResponse
{
    private List<RoomResponse> rooms;

    public RoomListResponse()
    {
        rooms = new ArrayList<>();
    }

    public void add(List<RoomResponse> list)
    {
        rooms.clear();
        rooms.addAll(list);
    }

    public List<RoomResponse> getRooms()
    {
        return rooms;
    }
}
