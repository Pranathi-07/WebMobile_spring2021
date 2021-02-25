import { Injectable } from '@angular/core';
import {environment as env} from './../../environments/environment';
import {HttpClient} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchRecipeService {
  public FOURSQUARE_API_URL = `https://api.foursquare.com/v2/venues/explore?client_id=${env.CLIENT_ID}&client_secret=${env.CLIENT_SECRET}&v=20180323&near=`;
  
  constructor(private http:HttpClient) { }

  getVenueByItem(placeName: String, recipeName: String): Observable<any[]> {
    const url = this.FOURSQUARE_API_URL + placeName + `&query=${recipeName}`;
    return this.http.get(url).pipe(map(venues => {
      return venues['response'].groups[0].items;
    }))
  }

  getRecipes(recipeName:String): Observable<any>  {
    const EDAMAM_API_URL = `https://api.edamam.com/search?q=${recipeName}&app_id=${env.APP_ID}&app_key=${env.APP_KEY}`;
    return this.http.get(EDAMAM_API_URL).pipe(map(e => e['hits']))
  }
}
