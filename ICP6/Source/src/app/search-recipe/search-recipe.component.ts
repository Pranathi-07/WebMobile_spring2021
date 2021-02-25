import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { SearchRecipeService } from './search-recipe.service';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private searchService: SearchRecipeService) {
    
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      /**
       * Write code to get recipe
       */
      this.searchService.getRecipes(this.recipeValue).subscribe(data => {
        this.recipeList = data.map(e => e.recipe)
        console.log(this.recipeList)
      })
    }

    if (this.placeValue !== null && this.placeValue !== '' && this.recipeValue !== null && this.recipeValue !== '') {
      /**
       * Write code to get place
       */
      this.searchService.getVenueByItem(this.placeValue, this.recipeValue).subscribe(data => {
        this.venueList = data.map(e => e.venue);
        console.log(this.venueList)
      })
    }
  }
}
