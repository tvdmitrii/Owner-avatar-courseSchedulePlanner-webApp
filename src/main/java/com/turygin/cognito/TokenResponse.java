package com.turygin.cognito;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Cognito Token response.
 */
public class TokenResponse {

	/** Access token for AWS authentication. Unused. */
	@JsonbProperty("access_token")
	private String accessToken;

	/** Refresh token that can be used to renew the access token. Unused. */
	@JsonbProperty("refresh_token")
	private String refreshToken;

	/** ID token containing user claims. */
	@JsonbProperty("id_token")
	private String idToken;

	/** Defines token type. In our case bearer token. Unused. */
	@JsonbProperty("token_type")
	private String tokenType;

	/** Defines when token will expire. Unused. */
	@JsonbProperty("expires_in")
	private int expiresIn;

	/**
	 * Gets access token.
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets access token.
	 * @param accessToken the access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets expires in.
	 * @return the expires in
	 */
	public int getExpiresIn() {
		return expiresIn;
	}

	/**
	 * Sets expires in.
	 * @param expiresIn the expires in
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * Gets id token.
	 * @return the id token
	 */
	public String getIdToken() {
		return idToken;
	}

	/**
	 * Sets id token.
	 * @param idToken the id token
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	/**
	 * Gets refresh token.
	 * @return the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Sets refresh token.
	 * @param refreshToken the refresh token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Gets token type.
	 * @return the token type
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * Sets token type.
	 * @param tokenType the token type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Generate string representation of token response for debugging purposes.
	 * @return string representation of token response
	 */
	@Override
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