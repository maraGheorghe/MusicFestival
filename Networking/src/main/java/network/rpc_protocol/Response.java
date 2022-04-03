package network.rpc_protocol;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType type;
    private Object data;

    private Response() {}

    public ResponseType getType() {
        return this.type;
    }

    public Object getData() {
        return this.data;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response {" +
                "type= " + type +
                ", data= " + data +
                '}';
    }

    public static class Builder {
        private Response response = new Response();

        public Builder type(ResponseType type) {
            response.setType(type);
            return this;
        }

        public Builder data(Object data) {
            response.setData(data);
            return this;
        }

        public Response build() {
            return response;
        }
    }

}
