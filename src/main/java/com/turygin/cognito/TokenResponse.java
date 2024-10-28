package com.turygin.cognito;

import jakarta.json.bind.annotation.JsonbProperty;

public class TokenResponse {

	@JsonbProperty("access_token")
	private String accessToken;

	@JsonbProperty("refresh_token")
	private String refreshToken;

	@JsonbProperty("id_token")
	private String idToken;

	@JsonbProperty("token_type")
	private String tokenType;

	@JsonbProperty("expires_in")
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TokenResponse {accessToken=");
		builder.append(accessToken);
		builder.append(", refreshToken=");
		builder.append(refreshToken);
		builder.append(", idToken=");
		builder.append(idToken);
		builder.append(", tokenType=");
		builder.append(tokenType);
		builder.append(", expiresIn=");
		builder.append(expiresIn);
		builder.append("}");
		return builder.toString();
	}
}