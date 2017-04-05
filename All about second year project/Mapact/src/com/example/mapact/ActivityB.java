package com.example.mapact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.accessweb.parsers.LocationParser;
import com.google.android.gms.analytics.HitBuilders.SocialBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("NewApi")
public class ActivityB extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener,
		OnItemClickListener {
	// //////////////////////////////////////////////////////////// VARIABLES
	// //////////////////////

	private static final int GPS_ERRORDIALOG_REQUEST = 0;
	public GoogleMap mMap;
	public String USER_EMI_ID; // uer id to identify user uniquely.
	// MapView mMapView; // map view method
	private static final double COLOMBO_LAT = 6.928091,
			COLOMBO_LNG = 79.864542;
	private static final float DEFAULTZOOM = 10;
	private MarkerOptions moption = null;
	private Marker marker, myposition;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	ArrayList<Marker> markersTraffic = new ArrayList<Marker>();
	static final int POLYGON_POINTS = 3;
	Polygon shape;
	Polyline line;
	Circle circles;
	ArrayList<location> loc_array = new ArrayList<location>();
	Timer timer;
	MyTimerTask myTimerTask;
	GPSTracker gs;
	public location currntLocation, result_Location;
	public int i = 0;
	GMapV2Direction md;
	LatLng COLOMBO_LATLNG = new LatLng(COLOMBO_LAT, COLOMBO_LNG);
	Button btnContinue, aboutUs;
	// request
	int DELAY = 5000;
	int REPEAT = 10000;

	LatLng fromPosition = new LatLng(6.45072872, 80.1728716492);
	LatLng toPosition = new LatLng(COLOMBO_LAT, COLOMBO_LNG);
	//
	private DrawerLayout drawerLayout;
	private ListView listView;
	private myAdapter myadapter;
	private ActionBarDrawerToggle drawerlistner;

	// ////////////////////////////////////////////////////////////-----------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (serviceOK()) {
			setContentView(R.layout.activity_map);

			drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
			listView = (ListView) findViewById(R.id.drawerList);
			myadapter = new myAdapter(this);
			listView.setAdapter(myadapter);
			listView.setOnItemClickListener(this);
			drawerlistner = new ActionBarDrawerToggle(this, drawerLayout,
					R.drawable.ic_drawer, R.string.drawerOpen,
					R.string.drawerClose) {
				
				@Override
				public void onDrawerOpened(View drawerView) {
					// code when nessasery
				}

				@Override
				public void onDrawerClosed(View drawerView) {
					// code when nessasery

				}

			};
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			drawerLayout.setDrawerListener(drawerlistner);
			
			if (initMap()) {
				// mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				USER_EMI_ID = finduserId();

				Toaster("Welcome to crowd source traffic application!");
				setMapAllTrafficItems();

				// default location..
				gotoLocation(COLOMBO_LAT, COLOMBO_LNG, DEFAULTZOOM);

				// starting webservice ------------------------
				if (isOnline()) {
					// should be start in onResume() :D
					/*
					 * if (timer != null) { timer.cancel(); } timer = new
					 * Timer(); myTimerTask = new MyTimerTask(); // delay
					 * 1000ms, repeat in 5000ms timer.schedule(myTimerTask,
					 * DELAY,REPEAT);
					 */
					Toaster("Please touch and hold screen to set your destination.");

					// setRoute();
					// code here.. {...}

					// drawDirectionToDestination(fromPosition, toPosition);

				} else {

					Toaster("Network  Unavailable! \nplease restart your application or check your internet Connection!");
				}

			} else {

				Toaster("Cannot initiate Map.");
			}

		} else {

			setContentView(R.layout.activity_main);
		}
		// --------
		// }
		// });

	}

	public void setMapAllTrafficItems() {
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(true);
		mMap.getUiSettings().setTiltGesturesEnabled(true);
		mMap.setTrafficEnabled(true);
		mMap.setBuildingsEnabled(true);
	}

	// public void

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		// Toast.makeText(this,"map changed to "+planets[position],
		// Toast.LENGTH_SHORT).show();
		switch (position) {
		case 0:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			setMapAllTrafficItems();

			break;
		case 1:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			setMapAllTrafficItems();
			break;
		case 2:
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			setMapAllTrafficItems();

			break;
		case 3:
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			setMapAllTrafficItems();
			break;
		case 4:
			// redirect to facebook
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/pages/Crowd-Source-Traffic-Data/866837240054576?__mref=message_bubble"));
			startActivity(browserIntent);
			break;
		case 5:
			// Tharanga`s website
			Intent browserIntent1 = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://marine-prabudda.rhcloud.com"));
			startActivity(browserIntent1);
			break;
		default:
			break;

		}

	}

	public void setInvisibleRoute() {
		removeEverything();
		if (line != null) {
			line.remove();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("EXIT")
				.setIcon(R.drawable.warning_2)
				.setMessage("Are you sure you want to Exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// finish();
								finishAffinity();
							}

						}).setNegativeButton("No", null).show();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerlistner.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		drawerlistner.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		myTimerTask = new MyTimerTask();
		// delay 1000ms, repeat in 5000ms
		timer.schedule(myTimerTask, DELAY, REPEAT);

		if (mMap != null) {
			MapStateManager mgr = new MapStateManager(this);
			CameraPosition position = mgr.getSavedCameraPosition();
			float maptype = mgr.getMapType();

			if (position != null) {
				CameraUpdate update = CameraUpdateFactory
						.newCameraPosition(position);
				mMap.moveCamera(update);
				mMap.setMapType((int) maptype);

			}
		}
		// ************ WRITE HERE TO NOT CLEARE MARKERS ***********************
		// mMapView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mMap != null) {
			MapStateManager mgr = new MapStateManager(this);
			mgr.saveMapState(mMap);
		}
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// mMapView.onSaveInstanceState(outState);

	}

	@Override
	public void onConnected(Bundle arg0) {

		Toaster("Connection was established!");
		// LocationRequest request = LocationRequest.create();
		// request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// request.setInterval(5000);
		// request.setFastestInterval(1000);

	}

	@Override
	public void onLocationChanged(Location location) {

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(17).tilt(40)
				.build();

		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	// changing map view
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerlistner.onOptionsItemSelected(item)) {
			return true;
		} else {

			switch (item.getItemId()) {
			case R.id.search:

				return true;
			case R.id.help:
				Intent i = new Intent(ActivityB.this, help.class);
				startActivity(i);
				break;
			case R.id.latlngDisplayer:
				displayLatLng();
				break;
			case R.id.ExitMap:
				finishAffinity();
				System.exit(0);

				break;
			default:
				break;

			}
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/** Create an option menu from res/menu/items.xml */
		getMenuInflater().inflate(R.menu.main, menu);

		/** Get the action view of the menu item whose id is search */
		View v = (View) menu.findItem(R.id.search).getActionView();

		/** Get the edit text from the action view */
		EditText txtSearch = (EditText) v.findViewById(R.id.txt_search);

		/** Setting an action listener */
		txtSearch.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					try {

						geoLocate(v.getText().toString());
						hideSoftKeyboard(v);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toaster("cannot find location please re-enter you location.");
					}
				}
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toaster("connection failed");
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	// DONNOT CHANG ANYTHING ....
	public boolean serviceOK() {

		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toaster("Cant connect to googleplay services");
		}
		return false;

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}

	}

	// here is the initiallation of the map
	private boolean initMap() {
		if (mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			mMap = mapFrag.getMap();

			if (mMap != null)
				mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(
								R.layout.info_windo, null);
						TextView tvLocality = (TextView) v
								.findViewById(R.id.tv_locality);
						// TextView tvLat = (TextView)
						// v.findViewById(R.id.tv_lat);
						// TextView tvLng = (TextView)
						// v.findViewById(R.id.tv_lng);
						TextView tvSnippet = (TextView) v
								.findViewById(R.id.tv_snippet);
						TextView tvRoadID = (TextView) v
								.findViewById(R.id.road_ID);

						LatLng llo = marker.getPosition();

						tvLocality.setText(marker.getTitle());
						// tvLat.setText("Latitude :" + llo.latitude);
						// tvLng.setText("Longitude :" + llo.longitude);
						tvSnippet.setText(marker.getSnippet() + "\n");
						// tvRoadID.setText();
						// //////////////
						Geocoder gc = new Geocoder(ActivityB.this);
						List<Address> list = null;

						try {
							list = gc.getFromLocation(llo.latitude,
									llo.longitude, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Address add = list.get(0);

						String roadID = add.getThoroughfare();
						String roadID_sub = add.getSubThoroughfare();
						//String latLang = add.getLatitude()+" and Longitude "+add.getLongitude();
						if(roadID==null){
							roadID="";
						}
						if(roadID_sub==null){
							roadID_sub="";
						}
						tvRoadID.setText("road :" + roadID + "\nsub road :"
								+ roadID_sub);

						// //////////////
						return v;
					}
				});

			mMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng arg0) {
					// removeEverything();
					setInvisibleRoute();
					// ??
					// remove custom markers only
				}
			});
			mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

				@Override
				public void onMapLongClick(LatLng ll) {
					removeEverything();
					if (line != null) {
						line.remove();
					}
					// setInvisibleRoute();
					Geocoder gc = new Geocoder(ActivityB.this);
					List<Address> list = null;
					try {
						if (mMap.isMyLocationEnabled()) {
							double myLat = mMap.getMyLocation().getLatitude();
							double myLng = mMap.getMyLocation().getLongitude();
							double desLat = ll.latitude;
							double desLng = ll.longitude;

							try {
								list = gc.getFromLocation(ll.latitude,
										ll.longitude, 1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Address add = list.get(0);
							ActivityB.this.setMarker(add.getLocality(),
									add.getCountryName(), ll.latitude,
									ll.longitude);

							if (myLat > 0 && myLng > 0) {
								drawDirectionToDestination(new LatLng(myLat,
										myLng), new LatLng(desLat, desLng));
							}
 
						} else {
							Toaster("please wait. try again");
						}
					} catch (Exception d) {
						Toaster("please wait. try again");
					}
				}
			});

			mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
//
//					String msg = marker.getTitle() + "Latitude:"
//							+ marker.getPosition().latitude + "\nLognitude"
//							+ marker.getPosition().longitude + ")";
//					Toaster(msg);

					return false;
				}
			});

			mMap.setOnMarkerDragListener(new OnMarkerDragListener() {

				@Override
				public void onMarkerDragStart(Marker arg0) {

				}

				@Override
				public void onMarkerDragEnd(Marker marker) {
					Geocoder gc = new Geocoder(ActivityB.this);
					List<Address> list = null;
					LatLng ll = marker.getPosition();
					try {
						list = gc.getFromLocation(ll.latitude, ll.longitude, 1);

					} catch (IOException e) {
						e.printStackTrace();
					}
					Address add = list.get(0);
					// add.getThoroughfare();
					// add.getSubThoroughfare();

					marker.setTitle(add.getLocality());
					marker.setSnippet(add.getCountryName());
					marker.showInfoWindow();
				}

				@Override
				public void onMarkerDrag(Marker arg0) {

				}
			});
		}

		return (mMap != null);
	}

	// to hide the keyboard
	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);

	}

	// set route
	public void setRoute() {

		drawDirectionToDestination(fromPosition, toPosition);

	}

	// to show direction
	@SuppressLint("NewApi")
	public void drawDirectionToDestination(LatLng fromPosition,
			LatLng toPosition) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		md = new GMapV2Direction();

		// mMap.addMarker(new MarkerOptions().position(fromPosition)
		// .title("Start"));
		// mMap.addMarker(new
		// MarkerOptions().position(toPosition).title("End"));

		Document doc = md.getDocument(fromPosition, toPosition,
				GMapV2Direction.MODE_DRIVING);
		int duration = md.getDurationValue(doc);
		String distance = md.getDistanceText(doc);
		String start_address = md.getStartAddress(doc);
		String copy_right = md.getCopyRights(doc);

		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(6).color(
				Color.CYAN);

		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}

		line = mMap.addPolyline(rectLine);

	}

	// /////////////////GEO LOCATION METHOD
	// able to be locate/move when we give address/zip
	public void geoLocate(String s) throws IOException {

		// hideSoftKeyboard(v);

		// EditText et = (EditText) findViewById(R.id.editText1);
		String location = s;

		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location, 2);

		if (list.isEmpty() == false) {
			Address add = list.get(0);
			String locality = add.getLocality();
			Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();
			// gettin lng and lati ti variables
			double lat = add.getLatitude();
			double lng = add.getLongitude();

			// ///////////////////////////////////////
			gotoLocation(lat, lng, DEFAULTZOOM);

			setMarker(locality, lat, lng);
			location l = GPSgetter();
			try {
				drawDirectionToDestination(
						new LatLng(l.getLatitude(), l.getLongitude()),
						new LatLng(lat, lng));
			} catch (Exception yu) {
				Toaster("please wait!! and try again!");
			}
			/*
			 * if (marker != null) { marker.remove(); } //marker Option moption
			 * = new MarkerOptions() .position(new LatLng(lat,
			 * lng)).alpha(0.8f).visible(true);
			 * 
			 * marker = mMap.addMarker(moption);
			 */

		} else {
			Toast.makeText(this, " location is not available",
					Toast.LENGTH_SHORT).show();

		}

	}

	// display mylocation
	public void displayLatLng() {

		
		double latitude = mMap.getMyLocation().getLatitude();
		double longitude = mMap.getMyLocation().getLongitude();
		float speed = mMap.getMyLocation().getSpeed();

		// String lat = mMap.getCameraPosition().toString();
		//change speed para
		Toast.makeText(
				this,
				"Your position is \nLatitude :" + latitude + "\nLongitude :"
						+ longitude + "\nspeed:" + speed + "\nUser id :"
						+ USER_EMI_ID, Toast.LENGTH_LONG).show();

	}

	// webservice calling part --------------------------------------WEB SERVICE
	// ---------------
	// TO SEARCH LOCALITY

	// to get current position..
	public location GPSgetter() {

		gs = new GPSTracker(ActivityB.this);
		if (gs.canGetLocation) {

			location l = new location();
			l.setLatitude(mMap.getMyLocation().getLatitude());
			l.setLongitude(mMap.getMyLocation().getLongitude());
			l.setSpeed(mMap.getMyLocation().getSpeed());

			// l.setLatitude(gs.getLatitude());
			// l.setLongitude(gs.getLongitude());
			// l.setSpeed(gs.getSpeed());
			return l;

		} else {
			gs.showSettingsAlert();
			return null;
		}

	}

	// to find the user id unique for user
	public String finduserId() {
		String r;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			r = telephonyManager.getDeviceId();
			return r;
		} catch (Exception d) {
			r = null;
			return r;
		}
		// return r;// **** please make correct here

	}

	// move camara to specific location...
	private void gotoLocation(double lat, double lng, float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.animateCamera(update);
	}

	private void gotoLocation(double lat, double lng) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
		mMap.animateCamera(update);

	}

	private void setMarker(String locality, String country, double lat,
			double lng) {

		// removeEverything();

		MarkerOptions options = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title(locality)
				.alpha(0.8f)
				.anchor(0.7f, 0.7f)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.maker))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
				.draggable(true);
		// .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

		if (country.length() > 0) {
			options.snippet(country);
		}

		markers.add(mMap.addMarker(options));

		// ------------------------ reference
		// .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		// icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)
		// ------------------------
		// marker = mMap.addMarker(options);

	}

	// this is for searching perposes
	private void setMarker(String locality, double lat, double lng) {
		// removeEverything();
		if (marker != null) {
			marker.remove();
		}
		moption = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title(locality)
				.alpha(0.8f)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.maker))
				.draggable(true);

		// ------------------------ reference
		// .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		// .icon(BitmapDescriptorFactory.fromResource(R.drawable.traffic);
		// ------------------------

		// marker = mMap.addMarker(moption);
		markers.add(mMap.addMarker(moption));
	}

	private void setMarker(location l, double speed) {
		// removeTrafficMarkers();
		if (speed <= 20) {
			try {

				moption = new MarkerOptions()
						.position(new LatLng(l.getLatitude(), l.getLongitude()))
						.alpha(0.8f)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.maker))
						.draggable(true);

				// marker = mMap.addMarker(moption);
				markersTraffic.add(mMap.addMarker(moption));
			} catch (Exception s) {

			}
		} else if (speed > 20 && speed <= 40) {

			try {

				moption = new MarkerOptions()
						.position(new LatLng(l.getLatitude(), l.getLongitude()))
						.alpha(0.8f)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.mediumtraffic))
						.draggable(true);

				// marker = mMap.addMarker(moption);
				markersTraffic.add(mMap.addMarker(moption));
			} catch (Exception s) {

			}

		} else if (speed > 40 && speed <= 60) {
			try {

				moption = new MarkerOptions()
						.position(new LatLng(l.getLatitude(), l.getLongitude()))
						.alpha(0.8f)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.lowtraffic))
						.draggable(true);

				// marker = mMap.addMarker(moption);
				markersTraffic.add(mMap.addMarker(moption));
			} catch (Exception s) {

			}
		} else if (speed > 60) {
			try {

				moption = new MarkerOptions()
						.position(new LatLng(l.getLatitude(), l.getLongitude()))
						.alpha(0.8f)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.verylowtraffic))
						.draggable(true);

				// marker = mMap.addMarker(moption);
				markersTraffic.add(mMap.addMarker(moption));
			} catch (Exception s) {

			}
		}

	}

	private void setTraffifMarker(location l) {
		// remove_my_Marker();
		moption = new MarkerOptions()
				.position(new LatLng(l.getLatitude(), l.getLongitude()))
				.alpha(0.8f)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
				// .icon(BitmapDescriptorFactory.fromResource(R.drawable.traffic2))
				.draggable(true);

		myposition = mMap.addMarker(moption);
		// markers.add(mMap.addMarker(moption));
	}

	// for tempory
	private void removeTrafficMarkers() {

		if (!markersTraffic.isEmpty()) {
			for (Marker marker : markersTraffic) {
				marker.remove();
			}
		}
		if (myposition != null) {
			myposition.remove();

		}
	}

	public void removeEverything() {

		if (!markers.isEmpty()) {
			for (Marker marker : markers) {
				marker.remove();
			}
			markers.clear();
		}

		if (this.marker != null)
			this.marker.remove();

	}

	private void requestData(String uri) {
		MyTask task = new MyTask();
		task.execute(uri);

	}

	private void DisplayLatLang(location lc) {

		try {
			Toaster("\nWebservice : Location details  \nlatitude : "
					+ lc.getLatitude() + "\nlongitude : " + lc.getLongitude()
					+ "\nLocation Name :" + lc.getLocation_name()
					+ "\nSpeed is : " + lc.getSpeed());
			setTraffifMarker(lc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class MyTimerTask extends TimerTask {
		int i = 0;

		@Override
		public void run() {

			i++;
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					try {

						currntLocation = GPSgetter();

						// for cloud url CEP one method...
						  requestData(
						  "http://marine-prabudda.rhcloud.com/Application?phoneID="
						  + USER_EMI_ID + "&latitude=" +
						  currntLocation.getLatitude() + "&longitude=" +
						  currntLocation.getLongitude() + "&speed=" +
						  currntLocation.getSpeed());
						 
						/*// for local host CEP 2 method..
						requestData("http://192.168.43.45:9763/road-traffic/apis/traffic?phoneID="
								+ USER_EMI_ID
								+ "&latitude="
								+ currntLocation.getLatitude()
								+ "&longitude="
								+ currntLocation.getLongitude()
								+ "&speed="
								+ currntLocation.getSpeed());*/

					} catch (Exception e) {
						e.printStackTrace();
						Log.e("prabu", "didnt work request");
					}

					//Toaster("request sent :" + i);
				}
			});
		}

	}

	private class MyTask extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			// if no password
			String content = HttpManager.getData(params[0]);

			return content;

		}

		// executes after the background method
		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
				Toaster("cant acess web services!onPost");
				return;
			}
			// must be get array of location
			System.out.print(result);
			try {
				// result_Location = LocationParser.parseFeed(result);//for one
				// json obj

				loc_array = LocationParser.parseFeed(result);// for array

				removeTrafficMarkers();

				if (!loc_array.isEmpty()) {
					for (location l : loc_array) {
						// setTraffifMarker(l);

						setMarker(l, l.getSpeed());

					}
				} else {

				}

			} catch (Exception sd) {

			}

		}

		@Override
		protected void onProgressUpdate(String... values) {

		}

	}

	public void Toaster(String s) {

		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

}

class myAdapter extends BaseAdapter {
	private Context context;
	String[] menu;
	int[] images = { R.drawable.satellite, R.drawable.normal, R.drawable.globe,
			R.drawable.hybryd, R.drawable.facebook, R.drawable.visitus };

	public myAdapter(Context context) {
		this.context = context;
		menu = context.getResources().getStringArray(R.array.menu);

	}

	@Override
	public int getCount() {
		return menu.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menu[position];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View row = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_row, parent, false);
		} else {
			row = convertView;
		}

		TextView titleTextView = (TextView) row
				.findViewById(R.id.textViewcustom);
		ImageView titleImageView = (ImageView) row
				.findViewById(R.id.imageViewcustom);
		titleTextView.setText(menu[position]);
		titleImageView.setImageResource(images[position]);
		return row;
	}

}
